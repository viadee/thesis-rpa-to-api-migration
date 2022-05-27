<template>
  <div class="process-diagram-instance-list-sub-comp" v-if="instanceDataLoaded">
    <v-data-table
      height="389px"
      fixed-header
      :headers="headers"
      :items="instanceData"
      :items-per-page="7"
    >
      <template v-slot:item="row">
        <tr class="text-center">
          <td v-if="row.item.state" style="color: red">&#x2715;</td>
          <td v-else style="color: green">&#x2713;</td>
          <td>{{ row.item.instanceId }}</td>
          <td>{{ row.item.startTime }}</td>
          <td>{{ row.item.businessKey }}</td>
        </tr>
      </template>
      <template v-slot:[`footer.page-text`]>
        <v-btn color="grey" dark class="mg-2" @click="startInstance">
          Start Instance
        </v-btn>
        <v-dialog v-model="startInstanceDiaglog" width="500px">
          <v-card height="auto">
            <v-card-title>
              <span class="text-h5">Start Process Instance</span>
            </v-card-title>
            <v-card-text>
              <v-container>
                <v-row>
                  <v-col cols="4">
                    <v-subheader style="margin-top: 15px; margin-left: -10px"> Business Key </v-subheader>
                  </v-col>
                  <v-col cols="8">
                    <v-text-field v-model="businessKey"></v-text-field>
                  </v-col>
                </v-row>
                <v-row style="margin-left: -50px; margin-top: -30px; margin-bottom: -50px">
                  <v-col cols="12">
                <v-btn
                  @click="add"
                  class="ma-3"
                  color="black"
                  plain
                  :ripple="false"
                  >Add Variable</v-btn
                >
                  </v-col>
                </v-row>
                <v-expand-transition>
                  <div>
                <v-row v-for="(textField, i) in textFields" :key="i" style="margin-bottom: -50px">
                  <v-col cols="12" sm="6" md="5">
                    <v-text-field
                      v-model="textField.nameVal"
                      type="string"
                      :label="textField.nameKey"
                    ></v-text-field>
                  </v-col>
                  <v-col cols="12" sm="6" md="5">
                    <v-text-field
                      v-model="textField.valVal"
                      type="string"
                      :label="textField.valKey"
                    ></v-text-field>
                  </v-col>
                  <v-col cols="1" sm="6" md="2">
                    <v-btn
                      @click="remove(i)"
                      color="secondary"
                      fab
                      x-small
                      dark
                      style="margin-top: 10px"
                      >-</v-btn
                    >
                  </v-col>
                </v-row>
                </div>
                </v-expand-transition>
              </v-container>
            </v-card-text>

            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn color="blue darken-1" text @click="cancel()"> Cancel </v-btn>
              <v-btn color="blue darken-1" text @click="postInstance()"> Post </v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>
      </template>
    </v-data-table>
  </div>
</template>

<script>
import ProcessDiagramService from "../../services/Process Overview/ProcessDiagramService.js";
import ProcessDefinitionService from "../../services/Process Overview/ProcessDefinitionService.js"

export default {
  name: "ProcessDiagramInstanceListSubComp",
  data() {
    return {
      textFields: [],
      businessKey: "",
      headers: [
        { text: "State", align: "center", sortable: false },
        {
          text: "Instance-ID",
          value: "instanceId",
          align: "center",
          sortable: false,
        },
        { text: "Start Time", value: "startTime", align: "center" },
        {
          text: "Business Key",
          value: "businessKey",
          align: "center",
          sortable: false,
        },
      ],
      instanceDataLoaded: false,
      instanceData: [],
      startInstanceDiaglog: false,
    };
  },
  props: {
    procDefID: {
      type: String,
      required: true,
    },
  },
  methods: {
    add() {
      this.textFields.push({
        nameKey: "Name",
        nameVal: "",
        valKey: "Value",
        valVal: "",
      });
    },
    remove(idx) {
      this.textFields.splice(idx, 1);
    },
    startInstance() {
      this.startInstanceDiaglog = true;
    },
    postInstance() {
      var requestBodyForInstance = {};
      var variableDateForInstance = [];
      for(var p = 0; p<this.textFields.length; p++){
        var n = this.textFields[p].nameVal;
        var val = this.textFields[p].valVal;
        var v = {
          [n]: {
            "value": val,
            "type": "String"
          },
        }
        variableDateForInstance.push(v);
      }
      requestBodyForInstance = {
        businessKey: this.businessKey,
        variables: Object.assign({}, ...variableDateForInstance)
      }
      ProcessDefinitionService.postProcessInstance(requestBodyForInstance, this.procDefID).then(() => {
        this.getInstanceData(this.procDefID);
        this.$root.$emit('newProcessInstancePosted');
        this.cancel();
      })
    },
    cancel() {
      this.businessKey = "";
      this.textFields = [];
      this.startInstanceDiaglog = false;
    },
    async getInstanceData(id) {
      let instanceDataTemp = [];
      instanceDataTemp = await ProcessDiagramService.getAllProcessInstances(id);
      this.instanceData = instanceDataTemp.data;
    },
  },
  created() {
    this.getInstanceData(this.procDefID).then(() => {
      this.instanceDataLoaded = true;
    });
  },
  watch: {
    procDefID: function (val) {
      this.getInstanceData(val).then(() => {
        this.instanceDataLoaded = true;
      });
    },
  },
};
</script>

<style>
.process-diagram-instance-list-sub-comp {
  width: 100%;
  height: 100%;
  border-top: solid 1px;
  border-right: solid 1px;
  border-bottom: solid 1px;
}
</style>