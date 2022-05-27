<template>
  <div id="robot-bpmn-sub-comp" class="robot-bpmn-sub-comp">
    <link
      rel="stylesheet"
      href="https://unpkg.com/bpmn-js@8.8.2/dist/assets/diagram-js.css"
    />
  </div>
</template>

<script>
import BpmnJS from "bpmn-js/dist/bpmn-navigated-viewer.production.min.js";
import GetProcessDiagramService from "../../services/RPA/GetProcessDiagramService.js";

export default {
  name: "RobotBPMNSubCom",
  props: {
    processDefinitionId: {
      type: String,
      required: true,
    },
    activityId: {
      type: String,
      required: true,
    },
    activityIdUserTask: {
      type: String,
      required: true,
    },
    totalCasesHandled: {
      type: Number,
      required: true,
    },
    casesHandledManually: {
      type: Number,
      required: true,
    },
    executionFrequency: {
      type: Number,
      required: true,
    }
  },
  data: function () {
    return {
      xml: "",
    };
  },
  methods: {
    async loadBpmn() {
      var self = this;
      this.bpmnViewer = new BpmnJS({
        container: document.querySelector("#robot-bpmn-sub-comp"),
      });

      var xmlData = [];
      xmlData = await GetProcessDiagramService.getProcessDiagram(
        this.processDefinitionId
      );
      this.xml = xmlData.data.bpmn20Xml;

      this.bpmnViewer
        .importXML(this.xml)
        .then(function () {
          var canvas = self.bpmnViewer.get("canvas"),
            overlays = self.bpmnViewer.get("overlays");
          canvas.zoom("fit-viewport");
          canvas.addMarker(self.activityId, "highlight");
          overlays.add(self.activityId, {
            html: '<div class="tokenForCases">' + self.totalCasesHandled + "</div>",
            position: {
              right: 14,
              bottom: 14,
            },
          });
          overlays.add(self.activityIdUserTask, {
            html: '<div class="tokenForCases">' + self.casesHandledManually + "</div>",
            position: {
              right: 14,
              bottom: 14,
            },
          });
          overlays.add(self.activityId, {
            html: '<div class="unfinishedCasesFromPast">' + self.executionFrequency + "</div>",
            position: {
              right: 110,
              bottom: 14,
            },
          });
        })
        .catch(function (err) {
          console.error("error when loading bpmn diagram", err);
        });
    },
  },
  mounted: function () {
    this.loadBpmn();
  },
  beforeUnmount: function () {
    this.bpmnViewer.destroy();
  },
  watch: {
    activityId: function (val) {
      this.activityId = val;
      this.bpmnViewer.destroy();
      this.loadBpmn();
    },
  },
};
</script>

<style>
.robot-bpmn-sub-comp {
  width: 100%;
  height: 100%;
}
.highlight:not(.djs-connection) .djs-visual > :nth-child(1) {
  fill: rgb(187, 137, 165) !important; /* color elements as green */
}
.tokenForCases {
  background-color: rgb(6, 109, 20);
  /*background-color: rgb(64, 66, 65); */
  color: White;
  border-radius: 50%;
  font-family: Arial;
  font-size: 12px;
  width: 30px;
  height: 30px;
  line-height: 30px;
  text-align: center;
}
.unfinishedCasesFromPast {
  /* green: background-color: rgb(6, 109, 20); */
  background-color: rgb(64, 66, 65);;
  color: White;
  border-radius: 50%;
  font-family: Arial;
  font-size: 12px;
  width: 30px;
  height: 30px;
  line-height: 30px;
  text-align: center;
}
</style>