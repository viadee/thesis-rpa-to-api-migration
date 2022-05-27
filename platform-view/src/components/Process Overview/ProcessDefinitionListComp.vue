<template>
  <div class="process-definition-list-comp">
    <input
      type="file"
      style="display: none"
      ref="file"
      v-on:change="handleFileUpload($event)"
    />
    <v-btn class="mb-5" depressed v-on:click="$refs.file.click()" color="grey">
      &#x2B; Add/Update Process Definition
    </v-btn>

    <v-data-table
      :headers="headers"
      :items="processDefinitions"
      :items-per-page="5"
    >
      <template v-slot:item="row">
        <tr class="text-center">
          <td v-if="row.item.incidents > 0" style="color: red">&#x2715;</td>
          <td v-else style="color: green">&#x2713;</td>
          <td>{{ row.item.incidents }}</td>
          <td>{{ row.item.instances }}</td>
          <td>{{ row.item.name }}</td>
          <td>
            <v-btn
              icon
              color="grey"
              v-on:click="viewProcessDefinitionDetails(row.item.name)"
            >
              <v-icon>mdi-file-image</v-icon>
            </v-btn>
            <v-btn
              icon
              color="grey"
              v-on:click="getXMLofProcessDefinition(row.item.name)"
            >
              <v-icon>mdi-download</v-icon>
            </v-btn>
            <v-btn
              icon
              color="grey"
              v-on:click="deleteProcessDefinitionsByKey(row.item.name)"
            >
              <v-icon>mdi-delete</v-icon>
            </v-btn>
          </td>
        </tr>
      </template>
    </v-data-table>

    <div v-if="isProcessDetails" class="process-diagram">
        <process-diagram-comp :processKey="keyForProcessDetails" />
    </div>

  </div>
</template>

<script>
import ProcessDefinitionService from "../../services/Process Overview/ProcessDefinitionService.js";
import ProcessDiagramComp from './ProcessDiagramComp.vue';

export default {
  components: { ProcessDiagramComp },
  name: "ProcessDefinitionListComp",
  data() {
    return {
      isProcessDetails: false,
      keyForProcessDetails: "",
      headers: [
        { text: "State", sortable: false, align: "center" },
        {
          text: "Incidents",
          value: "incidents",
          sortable: false,
          align: "center",
        },
        {
          text: "Instances",
          value: "instances",
          sortable: false,
          align: "center",
        },
        {
          text: "Process Key",
          value: "name",
          sortable: false,
          align: "center",
        },
        { text: "Action", sortable: false, align: "center" },
      ],
      processDefinitions: [],
      file: "",
    };
  },
  methods: {
    getProcessDefinitions() {
      ProcessDefinitionService.getProcessDefinitions().then((response) => {
        this.processDefinitions = response.data;
      });
    },
    handleFileUpload() {
      this.file = event.target.files[0];
      this.submitFile();
    },
    submitFile() {
      let formData = new FormData();
      formData.append("file", this.file);
      ProcessDefinitionService.uploadProcessDefinition(formData).then(() => {
        this.getProcessDefinitions();
      });
    },
    deleteProcessDefinitionsByKey(processKey) {
      ProcessDefinitionService.deleteProcessDefinitionsByKey(processKey).then(
        () => {
          this.getProcessDefinitions();
        }
      );
    },
    getXMLofProcessDefinition(processKey) {
      ProcessDefinitionService.getXMLofProcessDefinition(processKey).then(
        (response) => {
          let xml = response.data.bpmn20Xml;
          let element = document.createElement("a");
          let filename = processKey + ".bpmn";
          let blob = new Blob([xml], { type: "xml/bpmn" });
          element.setAttribute("href", window.URL.createObjectURL(blob));
          element.setAttribute("download", filename);
          element.dataset.downloadurl = [
            "xml/bpmn",
            element.download,
            element.href,
          ].join(":");
          element.draggable = true;
          element.classList.add("dragout");
          element.click();
        }
      );
    },
    viewProcessDefinitionDetails(processKey){
        this.keyForProcessDetails = processKey;
        console.log("see details of " + this.keyForProcessDetails);
        this.isProcessDetails = true;
    }
  },
  created() {
    this.getProcessDefinitions();
    this.$root.$on('newProcessInstancePosted', () => {
      this.getProcessDefinitions();
    })
  },
  beforeDestroy() {
    this.$root.$off('newProcessInstancePosted');
  },
};
</script>

<style>
.process-definition-list-comp {
  margin-top: 20px;
}
</style>