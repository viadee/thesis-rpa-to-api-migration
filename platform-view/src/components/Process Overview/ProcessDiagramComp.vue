<template>
  <div v-if="loaded" class="process-diagram-comp">
    <v-select
      class="select"
      v-bind:label="
        'Choose Version of Process with Key &quot;' + processKey + ' &quot;'
      "
      :items="versionData"
      :item-text="(item) => item.version"
      :item-value="(item) => item.id"
      v-model="procDefId"
    >
    </v-select>

    <div class="bpmn-container">
      <div class="bpmn-canvas">
        <process-diagram-bpmn-sub-comp v-bind:procDefID="procDefId" />
      </div>
      <div class="bpmn-instance-list">
          <process-diagram-instance-list-sub-comp v-bind:procDefID="procDefId" />
      </div>
    </div>
  </div>
</template>

<script>
import ProcessDiagramBPMNSubComp from "./ProcessDiagramBPMNSubComp.vue";
import ProcessDiagramInstanceListSubComp from "./ProcessDiagramInstanceListSubComp.vue";
import ProcessDiagramService from "../../services/Process Overview/ProcessDiagramService.js";

export default {
  name: "ProcessDiagramComp",
  props: {
    processKey: {
      type: String,
      required: true,
    },
  },
  data() {
    return {
      key: "",
      procDefId: "",
      versionData: [],
      loaded: false,
    };
  },
  components: {
    "process-diagram-bpmn-sub-comp": ProcessDiagramBPMNSubComp,
    "process-diagram-instance-list-sub-comp": ProcessDiagramInstanceListSubComp
  },
  methods: {
    async retrieveVersionDataAndSetProcDefId() {
      var versionDataTemp = [];
      versionDataTemp =
        await ProcessDiagramService.getAllVersionDataOfProcessKey(this.key);
      this.versionData = versionDataTemp.data;
      this.versionData.sort(function (a, b) {
        var v1 = a.version,
          v2 = b.version;
        if (v1 < v2) return -1;
        if (v1 > v2) return 1;
        return 0;
      });
      this.procDefId = this.versionData[this.versionData.length - 1].id;
      this.loaded = true;
    },
  },
  mounted() {
    this.key = this.processKey;
    this.retrieveVersionDataAndSetProcDefId();
  },
  watch: {
    processKey: function (val) {
      this.key = val;
      this.retrieveVersionDataAndSetProcDefId();
    },
  },
};
</script>

<style>
.select {
  width: 50%;
}
.bpmn-container {
  width: 100%;
  height: 450px;
  margin-bottom: 2em;
}
.bpmn-canvas {
  border: solid 1px;
  height: 100%;
  width: 50%;
  float: left;
}
.bpmn-instance-list {
  height: 100%;
  width: 50%;
  float: right;
}
</style>