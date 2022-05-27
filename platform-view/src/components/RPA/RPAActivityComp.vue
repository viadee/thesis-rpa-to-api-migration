<template>
  <div class="rpa-activity-comp">
    <div class="migration-container">
      <div class="bpmn-canvas">
        <RobotBPMNSubCom
          :processDefinitionId="rpaMetaData.definitionId"
          :activityId="rpaMetaData.activityId"
          :activityIdUserTask="rpaMetaData.activityIdUserTask"
          :totalCasesHandled="rpaMetaData.totalCasesHandled"
          :casesHandledManually="rpaMetaData.casesHandledManually"
          :executionFrequency="rpaMetaData.metaData.ef.orgVal"
        />
      </div>

      <div class="migration-details">
        <v-card class="mx-auto" outlined>
          <v-list-item style="margin-bottom: -30px">
            <v-list-item-content>
              <div class="text-overline mb-4">Dynamic Evaluation Criteria</div>
            </v-list-item-content>
          </v-list-item>
          <v-data-table
            :headers="headers"
            :items="dynamicRPAInformation"
            :hide-default-footer="true"
          >
          </v-data-table>
        </v-card>
        <v-list-item style="margin-bottom: -30px; margin-top: 10px">
          <v-list-item-content>
            <div class="text-overline mb-4">Derivation of Migration Urgency</div>
          </v-list-item-content>
        </v-list-item>
        <v-container fluid>
          <v-row>
            <v-col cols="2">
              <v-subheader>Dynamic Criteria</v-subheader>
            </v-col>
            <v-col cols="4">
              <v-text-field
                label="Weighted Sum of Assessment Values"
                v-model="weightedSumDynamicInformation"
                prefix="Σ"
                readonly
              ></v-text-field>
            </v-col>
            <v-col cols="2">
              <v-subheader>Static Criteria</v-subheader>
            </v-col>
            <v-col cols="4">
              <v-text-field
                label="Weighted Sum of Assessment Values"
                v-model="weightedSumStaticInformation"
                prefix="Σ"
                readonly
              ></v-text-field>
            </v-col>
          </v-row>

          <v-row style="margin-top: -30px">
            <v-col cols="2">
              <v-subheader>Migration Urgency</v-subheader>
            </v-col>
            <v-col cols="10">
              <v-text-field
                label=""
                v-model="textualCalculation"
                value="example"
                suffix="Percent (%)"
                readonly
              ></v-text-field>
            </v-col>
          </v-row>
        </v-container>
      </div>
    </div>
  </div>
</template>

<script>
import RobotBPMNSubCom from "./RobotBPMNSubComp.vue";

export default {
  name: "RPAActivityComp",
  components: { RobotBPMNSubCom },
  props: {
    rpaMetaData: {
      type: Array,
      required: true,
    },
  },
  data() {
    return {
      headers: [
        {
          text: "Dynamic Criterion",
          value: "name",
          sortable: false,
          align: "center",
        },
        {
          text: "Retrieved Information",
          value: "orgVal",
          sortable: false,
          align: "center",
        },
        {
          text: "Translated Rating",
          value: "value",
          sortable: false,
          align: "center",
        },
        {
          text: "Weight",
          value: "weight",
          sortable: false,
          align: "center",
        },
        {
          text: "Weighted Assessment Value",
          value: "weightedValue",
          sortable: false,
          align: "center",
        },
      ],
      dynamicRPAInformation: [],
      weightedSumDynamicInformation: 0,
      weightedSumStaticInformation: 0,
      baseline: 0,
      textualCalculation: "",
    };
  },
  methods: {
    getweightedSumDynamicInformation(dynamicRPAInformation) {
      var sum = 0;
      for (var c in dynamicRPAInformation) {
        var weightedval = dynamicRPAInformation[c].weightedValue;
        if (typeof weightedval === "string") {
          continue;
        } else {
          sum = sum + weightedval;
        }
      }
      return sum;
    },
    getDynamicData(d) {
      this.dynamicRPAInformation = [];
      this.weightedSumDynamicInformation = 0;
      this.weightedSumStaticInformation = 0;
      this.baseline = 0;
      this.textualCalculation = "";
      for (var c in d) {
        var n = d[c].name;
        if (
          ["automationRate", "executionFrequency", "frequencyOfReuse"].includes(
            n
          )
        ) {
          var orgV;
          if (n === "automationRate" && !isNaN(parseFloat(d[c].orgVal))) {
            orgV = Number(d[c].orgVal * 100).toFixed(2) + "%";
          } else {
            orgV = d[c].orgVal;
          }
          var newN = d[c].name.replace(/([A-Z])/g, " $1");
          var weightedval = d[c].value * d[c].weight;
          if (isNaN(weightedval)) {
            weightedval = "Criterion not considered.";
          } else {
            this.baseline = this.baseline + d[c].weight * d[c].scale;
          }
          this.dynamicRPAInformation.push({
            name: newN.charAt(0).toUpperCase() + newN.slice(1),
            orgVal: orgV,
            value: d[c].value,
            weight: d[c].weight,
            weightedValue: weightedval,
          });
        } else {
          this.weightedSumStaticInformation =
            this.weightedSumStaticInformation + d[c].value * d[c].weight;
          this.baseline = this.baseline + d[c].weight * d[c].scale;
        }
      }
      this.weightedSumDynamicInformation =
        this.getweightedSumDynamicInformation(this.dynamicRPAInformation);
      this.textualCalculation =
        "(" +
        this.weightedSumDynamicInformation +
        "+" +
        this.weightedSumStaticInformation +
        ")" +
        "/" +
        this.baseline +
        "=" +
        Number(
          ((this.weightedSumDynamicInformation +
            this.weightedSumStaticInformation) /
            this.baseline)*100
        ).toFixed(2);
    },
  },
  watch: {
    rpaMetaData: function (val) {
      this.getDynamicData(val.metaData);
    },
  },
  created() {
    this.getDynamicData(this.rpaMetaData.metaData);
  },
};
</script>

<style>
.migration-container {
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
.migration-details {
  border-top: solid 1px;
  border-right: solid 1px;
  border-bottom: solid 1px;
  height: 100%;
  width: 50%;
  float: right;
}
</style>