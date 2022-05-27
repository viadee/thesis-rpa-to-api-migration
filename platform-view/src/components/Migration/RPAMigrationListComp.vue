<template>
  <div class="rpa-migration-list-comp">
    <h2 style="margin-left: 0px">Overview of existing APIs</h2>
    <div class="swaggerSt" id="swagger"></div>
    <h2 style="margin-left: 0px">Data Mapping</h2>
    <div style="margin-left: 100px; margin-right: 100px; margin-top: 20px">
      <v-btn
        class="mb-5"
        depressed
        color="grey"
        v-on:click="showMappingDialog = true"
      >
        &#x2B; Create Interface Mapping
      </v-btn>

      <template>
        <v-dialog v-model="showMappingDialog" width="800">
          <v-card height="auto">
            <v-toolbar dark color="grey darken-3">
              <v-btn icon dark @click="closeMappingDialog()">
                <v-icon>mdi-close</v-icon>
              </v-btn>
              <v-toolbar-title>Data Mapping</v-toolbar-title>
              <v-spacer></v-spacer>
              <v-toolbar-items>
                <v-btn dark text @click="createMapping()" :loading="uploadSaveBtnLoading"> Save </v-btn>
              </v-toolbar-items>
            </v-toolbar>

            <v-container style="margin-top: 10px">
              <v-row>
                <v-col cols="6">
                  Topic
                  <v-text-field
                    outlined
                    dense
                    v-model="varMapping.apiTopic"
                  ></v-text-field>
                </v-col>
                <v-col cols="6">
                  Endpoint
                  <v-text-field
                    outlined
                    dense
                    v-model="varMapping.apiEndpoint"
                  ></v-text-field>
                </v-col>
              </v-row>

              <v-row style="margin-top: -20px">
                <v-col cols="12" sm="12">
                  Input Variables
                  <v-btn
                    @click="addInputVar()"
                    style="margin-left: 15px; margin-bottom: 2px"
                    icon
                    ><v-icon>mdi-plus</v-icon></v-btn
                  >
                </v-col>
              </v-row>

              <v-row
                style="margin-top: -30px; margin-bottom: -45px"
                v-if="!isInputListEmpty"
              >
                <v-col cols="12" sm="6">
                  <v-subheader class="pa-0">Process Variables</v-subheader>
                </v-col>
                <v-col cols="12" sm="6">
                  <v-subheader class="pa-0">API Parameters</v-subheader>
                </v-col>
              </v-row>

              <v-row
                v-for="(inputMappingField, i) in inputMappingFields"
                style="margin-top: -35px"
                :key="'I' + i"
              >
                <v-col cols="12" sm="6">
                  <v-text-field
                    dense
                    v-model="inputMappingField.processVar"
                    type="string"
                  ></v-text-field>
                </v-col>
                <v-col cols="12" sm="6">
                  <v-text-field
                    dense
                    append-outer-icon="mdi-minus"
                    @click:append-outer="removeInputField(i)"
                    v-model="inputMappingField.apiVar"
                    type="string"
                  ></v-text-field>
                </v-col>
              </v-row>

              <v-row style="margin-top: -20px">
                <v-col cols="12" sm="12">
                  Output Variables
                  <v-btn
                    @click="addOutputVar()"
                    style="margin-left: 3px; margin-bottom: 3px"
                    icon
                    ><v-icon>mdi-plus</v-icon></v-btn
                  >
                </v-col>
              </v-row>

              <v-row
                style="margin-top: -30px; margin-bottom: -45px"
                v-if="!isOutputListEmpty"
              >
                <v-col cols="12" sm="6">
                  <v-subheader class="pa-0">Process Variables</v-subheader>
                </v-col>
                <v-col cols="12" sm="6">
                  <v-subheader class="pa-0">API Parameters</v-subheader>
                </v-col>
              </v-row>

              <v-row
                v-for="(outputMappingField, i) in outputMappingFields"
                style="margin-top: -35px"
                :key="'O' + i"
              >
                <v-col cols="12" sm="6">
                  <v-text-field
                    dense
                    v-model="outputMappingField.processVar"
                    type="string"
                  ></v-text-field>
                </v-col>
                <v-col cols="12" sm="6">
                  <v-text-field
                    dense
                    append-outer-icon="mdi-minus"
                    @click:append-outer="removeOutputField(i)"
                    v-model="outputMappingField.apiVar"
                    type="string"
                  ></v-text-field>
                </v-col>
              </v-row>
            </v-container>
          </v-card>
        </v-dialog>
      </template>

      <v-data-table
        :headers="headers"
        :items="mappingDataOfTopics"
        group-by="apiTopic.id"
      >
        <template v-slot:item="row">
          <tr class="text-center">
            <td></td>
            <td>{{ row.item.varType }}</td>
            <td>{{ row.item.botVar }}</td>
            <td v-if="row.item.varType == 'IN'">
              <v-icon color="green">mdi-arrow-right-thin</v-icon>
            </td>
            <td v-else><v-icon color="red">mdi-arrow-left-thin</v-icon></td>
            <td>{{ row.item.apiVar }}</td>
          </tr>
        </template>

        <template v-slot:[`group.header`]="{ items, isOpen, toggle }">
          <td :colspan="headers.length">
            <v-icon @click="toggle">
              {{ isOpen ? "mdi-minus" : "mdi-plus" }}
            </v-icon>
            <span style="display: inline-block; margin-top: 6px"
              >Topic: <b>{{ items[0].apiTopic.id }}</b>
              <i>({{ items[0].apiTopic.restEndpoint }})</i></span
            >
            <span style="float: right; margin-top: 0px">
              <v-btn
                icon
                :loading="deleteBtnloading"
                color="grey"
                v-on:click="deleteApiTopic(items[0].apiTopic.id)"
              >
                <v-icon>mdi-delete</v-icon>
              </v-btn>
            </span>
          </td>
        </template>
      </v-data-table>
    </div>
  </div>
</template>

<script>
import SwaggerUI from "swagger-ui";
import "swagger-ui/dist/swagger-ui.css";
import InterfaceMappingService from "../../services/Migration/InterfaceMappingService.js";

export default {
  name: "RPAMigrationListComp",
  data() {
    return {
      showMappingDialog: false,
      mappingDataOfTopics: [],
      deleteBtnloading: false,
      uploadSaveBtnLoading: false,
      varMapping: [],
      inputMappingFields: [],
      isInputListEmpty: true,
      outputMappingFields: [],
      isOutputListEmpty: true,
      headers: [
        {
          text: "",
          value: "",
          sortable: false,
          align: "center",
          width: "250px",
        },
        {
          text: "Variable Type",
          value: "varType",
          sortable: false,
          align: "center",
        },
        {
          text: "Process Variable",
          value: "botVar",
          sortable: false,
          align: "center",
        },
        {
          text: "Direction",
          align: "center",
          sortable: false,
        },
        {
          text: "API Parameter",
          value: "apiVar",
          sortable: false,
          align: "center",
        },
      ],
    };
  },
  mounted() {
    const spec = require("../../assets/lineDiagnosisAPI.json");
    SwaggerUI({
      spec: spec,
      dom_id: "#swagger",
      defaultModelsExpandDepth: -1,
      docExpansion: "full",
    });
  },
  methods: {
    addInputVar() {
      this.inputMappingFields.push({
        processVar: "",
        apiVar: "",
      });
      this.isInputListEmpty = false;
    },
    removeInputField(idx) {
      this.inputMappingFields.splice(idx, 1);
      if (this.inputMappingFields.length == 0) {
        this.isInputListEmpty = true;
      }
    },
    addOutputVar() {
      this.outputMappingFields.push({
        processVar: "",
        apiVar: "",
      });
      this.isOutputListEmpty = false;
    },
    removeOutputField(idx) {
      this.outputMappingFields.splice(idx, 1);
      if (this.outputMappingFields.length == 0) {
        this.isOutputListEmpty = true;
      }
    },
    closeMappingDialog() {
      this.inputMappingFields = [];
      this.outputMappingFields = [];
      this.varMapping = [];
      this.isInputListEmpty = true;
      this.isOutputListEmpty = true;
      this.showMappingDialog = false;
    },
    async createMapping() {
      this.uploadSaveBtnLoading = true;
      var inputMappingToSent = {};
      var outputMappingToSent = {};
      for(var c=0; c<this.inputMappingFields.length; c++){
        inputMappingToSent = {
          apiVar: this.inputMappingFields[c].apiVar,
          botVar: this.inputMappingFields[c].processVar,
          apiTopicId: this.varMapping.apiTopic,
          varType: "IN",
          restEndpoint: this.varMapping.apiEndpoint
        }
        await InterfaceMappingService.createMappingForTopic(inputMappingToSent).then(() => {})
      }
      for(var cc=0; cc<this.outputMappingFields.length; cc++){
        outputMappingToSent = {
          apiVar: this.outputMappingFields[cc].apiVar,
          botVar: this.outputMappingFields[cc].processVar,
          apiTopicId: this.varMapping.apiTopic,
          varType: "OUT",
          restEndpoint: this.varMapping.apiEndpoint
        }
        await InterfaceMappingService.createMappingForTopic(outputMappingToSent).then(() => {})
      }
      await InterfaceMappingService.restartApiWorker().then(() => {});
      this.uploadSaveBtnLoading = false;
      this.closeMappingDialog();
      this.getMappingData();
    },
    getMappingData() {
      InterfaceMappingService.getMappingDataOfTopics().then((resp) => {
        this.mappingDataOfTopics = resp.data;
      });
    },
    deleteApiTopic(topicName) {
      this.deleteBtnloading = true;
      InterfaceMappingService.deleteMapppingDataAndTopic(topicName).then(() => {
        this.deleteBtnloading = false;
        this.getMappingData();
      });
    },
  },
  created() {
    this.getMappingData();
  },
};
</script>

<style>
.rpa-migration-list-comp {
  margin-top: 20px;
}
.swaggerSt {
  margin-top: -30px;
  margin-bottom: -20px;
  width: 100%;
}
</style>