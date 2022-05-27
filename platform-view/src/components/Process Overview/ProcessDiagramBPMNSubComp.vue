<template>
  <div id="process-diagram-bpmn-sub-comp" class="process-diagram-bpmn-sub-comp">
    <link
      rel="stylesheet"
      href="https://unpkg.com/bpmn-js@8.8.2/dist/assets/diagram-js.css"
    />
  </div>
</template>

<script>
import BpmnJS from "bpmn-js/dist/bpmn-navigated-viewer.production.min.js";
import ProcessDiagramService from "../../services/Process Overview/ProcessDiagramService.js";

export default {
  name: "ProcessDiagramBPMNSubComp",
  data: function () {
    return {
      xml: "",
      id: "",
      activityData: [],
      diagramXML: null,
    };
  },
  props: {
    procDefID: {
      type: String,
      required: true,
    },
  },
  methods: {
    async loadBpmn() {
      var self = this;
      this.bpmnViewer = new BpmnJS({
        container: document.querySelector("#process-diagram-bpmn-sub-comp"),
      });
      console.log("selected process definition id: " + this.id);

      var xmlData = [],
        actData = [];
      xmlData = await ProcessDiagramService.getProcessDiagram(this.id);
      this.xml = xmlData.data.bpmn20Xml;
      actData = await ProcessDiagramService.getProcessActivtiyInf(this.id);
      this.activityData = actData.data;

      this.bpmnViewer
        .importXML(this.xml)
        .then(function () {
          var canvas = self.bpmnViewer.get("canvas"),
            overlays = self.bpmnViewer.get("overlays"),
            eventBus = self.bpmnViewer.get("eventBus");

          canvas.zoom("fit-viewport");

          let actData = self.activityData;
          if (actData.length > 0) {
            for (let i = 0; i < actData.length; i++) {
              overlays.add(actData[i].id, {
                html: '<div class="token">' + actData[i].instances + "</div>",
                position: {
                  right: 110,
                  bottom: 14,
                },
              });
            }
          }

          eventBus.on("element.click", function (e) {
            console.log("element.click on", e.element.id);
          });
        })
        .catch(function (err) {
          console.error("error when loading bpmn diagram", err);
        });
    },
  },
  created() {
    this.$root.$on('newProcessInstancePosted', () => {
      this.activityData=[];
      this.xml="";
      this.bpmnViewer.destroy();
      this.loadBpmn();
    })
  },
  beforeDestroy() {
    this.$root.$off('newProcessInstancePosted');
  },
  mounted: function () {
    this.id = this.procDefID;
    this.loadBpmn();
  },
  beforeUnmount: function () {
    this.bpmnViewer.destroy();
  },
  watch: {
    procDefID: function (val) {
      this.id = val;
      this.bpmnViewer.destroy();
      this.loadBpmn();
    },
  },
};
</script>

<style>
.process-diagram-bpmn-sub-comp {
  width: 100%;
  height: 100%;
}
.token {
  /* green: background-color: rgb(6, 109, 20); */
  background-color: rgb(24, 26, 158);
  color: White;
  border-radius: 50%;
  font-family: Arial;
  font-size: 12px;
  width: 20px;
  height: 20px;
  line-height: 20px;
  text-align: center;
}
</style>