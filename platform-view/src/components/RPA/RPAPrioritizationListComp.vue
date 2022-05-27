<template>
  <div class="rpa-prioritization-list-comp">
    <v-card>
      <v-data-table
        :headers="headers"
        :items="prioritizationList"
        :sort-by.sync="sortBy"
        :sort-desc.sync="sortDesc"
        :search="search"
        @click:row="rowClick"
        item-key="topic"
        single-select
      >
        <template v-slot:top>
          <v-toolbar flat>
            <v-text-field
              v-model="search"
              append-icon="mdi-magnify"
              label="Search"
              single-line
              hide-details
            ></v-text-field>
            <v-spacer></v-spacer>
            <v-btn
              color="grey"
              depressed
              class="mg-2"
              @click="specifyWeightsOfCriteria()"
            >
              Specify Weights
            </v-btn>

            <v-dialog v-model="specifyWeightsDialog" max-width="500px">
              <v-card>
                <v-card-title>
                  <span class="text-h5">Specify Weights of Criteria</span>
                </v-card-title>

                <v-card-text>
                  <v-container>
                    <v-row>
                      <v-col md="12" style="margin-top: -30px">
                        <v-card-title>
                          <span
                            class="text-subtitle-2"
                            style="margin-bottom: -35px; margin-left: -30px"
                            >System Characteristics</span
                          >
                        </v-card-title>
                      </v-col>
                      <v-col cols="2" sm="6" md="6">
                        <v-text-field
                          v-model.number="
                            weightsOfCriteria.frontendStabilityWeight
                          "
                          type="number"
                          label="Frontend Stability"
                        ></v-text-field>
                      </v-col>
                      <v-col cols="12" sm="6" md="6">
                        <v-text-field
                          v-model.number="weightsOfCriteria.endOfLifeWeight"
                          type="number"
                          label="End of Life"
                        ></v-text-field>
                      </v-col>
                    </v-row>
                    <v-row>
                      <v-col md="12" style="margin-top: -30px">
                        <v-card-title>
                          <span
                            class="text-subtitle-2"
                            style="margin-bottom: -35px; margin-left: -30px"
                            >Robotic Performance</span
                          >
                        </v-card-title>
                      </v-col>
                      <v-col cols="12" sm="6" md="4">
                        <v-text-field
                          v-model.number="
                            weightsOfCriteria.automationRateWeight
                          "
                          type="number"
                          label="Automation Rate"
                        ></v-text-field>
                      </v-col>
                      <v-col cols="12" sm="6" md="4">
                        <v-text-field
                          v-model.number="
                            weightsOfCriteria.qualityOfResultsWeight
                          "
                          type="number"
                          label="Quality of Results"
                        ></v-text-field>
                      </v-col>
                      <v-col cols="12" sm="6" md="4">
                        <v-text-field
                          v-model.number="
                            weightsOfCriteria.numberOfBotRunnersWeight
                          "
                          type="number"
                          label="Number of Bot Runners"
                        ></v-text-field>
                      </v-col>
                    </v-row>
                    <v-row>
                      <v-col md="12" style="margin-top: -30px">
                        <v-card-title>
                          <span
                            class="text-subtitle-2"
                            style="margin-bottom: -35px; margin-left: -30px"
                            >Task Characteristics</span
                          >
                        </v-card-title>
                      </v-col>
                      <v-col cols="12" sm="6" md="6">
                        <v-text-field
                          v-model.number="
                            weightsOfCriteria.executionFrequencyWeight
                          "
                          type="number"
                          label="Execution Frequency"
                        ></v-text-field>
                      </v-col>
                      <v-col cols="12" sm="6" md="6">
                        <v-text-field
                          v-model.number="
                            weightsOfCriteria.numberOfSystemsWeight
                          "
                          type="number"
                          label="Number of Systems"
                        ></v-text-field>
                      </v-col>
                      <v-col cols="12" sm="6" md="4">
                        <v-text-field
                          v-model.number="
                            weightsOfCriteria.businessImpactWeight
                          "
                          type="number"
                          label="Business Impact"
                        ></v-text-field>
                      </v-col>
                      <v-col cols="12" sm="6" md="4">
                        <v-text-field
                          v-model.number="
                            weightsOfCriteria.regulatoryComplianceWeight
                          "
                          type="number"
                          label="Regulatory Compliance"
                        ></v-text-field>
                      </v-col>
                      <v-col cols="12" sm="6" md="4">
                        <v-text-field
                          v-model.number="
                            weightsOfCriteria.frequencyOfReuseWeight
                          "
                          type="number"
                          label="Frequency of Reuse"
                        ></v-text-field>
                      </v-col>
                    </v-row>
                  </v-container>
                </v-card-text>

                <v-card-actions>
                  <v-spacer></v-spacer>
                  <v-btn color="blue darken-1" text @click="close">
                    Cancel
                  </v-btn>
                  <v-btn color="blue darken-1" text @click="save"> Save </v-btn>
                </v-card-actions>
              </v-card>
            </v-dialog>
          </v-toolbar>
        </template>

        <template v-slot:[`item.prioritizationValue`]="{ item }">
          <v-chip :color="getColor(item.prioritizationValue)" dark>
            {{ item.prioritizationValue }}
          </v-chip>
        </template>
        <template v-slot:header>
          <thead>
            <tr style="background-color: rgb(238, 238, 238)">
              <th
                colspan="1"
                style="text-align: center; border-top: solid 1px lightgray"
              ></th>
              <th
                colspan="2"
                style="
                  text-align: center;
                  border-top: solid 1px lightgray;
                  border-left: solid 1px lightgray;
                "
              >
                System Characteristics
              </th>
              <th
                colspan="3"
                style="
                  text-align: center;
                  border-top: solid 1px lightgray;
                  border-left: solid 1px lightgray;
                "
              >
                Robotic Performance
              </th>
              <th
                colspan="5"
                style="
                  text-align: center;
                  border-top: solid 1px lightgray;
                  border-left: solid 1px lightgray;
                "
              >
                Task Characteristics
              </th>
              <th
                colspan="1"
                style="
                  text-align: center;
                  border-top: solid 1px lightgray;
                  border-left: solid 1px lightgray;
                "
              ></th>
            </tr>
          </thead>
        </template>
        <template v-slot:[`body.prepend`]>
          <tr>
            <td style="text-align: center"></td>
            <td style="text-align: center">4-Scale</td>
            <td style="text-align: center">4-Scale</td>
            <td style="text-align: center">4-Scale</td>
            <td style="text-align: center">4-Scale</td>
            <td style="text-align: center">4-Scale</td>
            <td style="text-align: center">2-Scale</td>
            <td style="text-align: center">2-Scale</td>
            <td style="text-align: center">4-Scale</td>
            <td style="text-align: center">2-Scale</td>
            <td style="text-align: center">4-Scale</td>
            <td style="text-align: center"></td>
          </tr>
        </template>
      </v-data-table>
    </v-card>

    <div v-if="isRobotDetails">
      <RPAActivityComp :rpaMetaData="rpaMetaData" />
    </div>
  </div>
</template>

<script>
import RPAActivityComp from "./RPAActivityComp.vue";
import RPABotService from "../../services/RPA/RPABotService.js";
import WeighingService from "../../services/RPA/WeighingService.js";

function evaluateCustomerWaitingTime(val) {
  var res;
  switch (true) {
    case val > 499:
      res = 4;
      return res;
    case val <= 499 && val > 250:
      res = 3;
      return res;
    case val <= 250 && val > 101:
      res = 2;
      return res;
    case val <= 100:
      res = 1;
      return res;
  }
  return val;
}

function evaluateAutomationRate(val) {
  var res;
  switch (true) {
    case val > 0.79:
      res = 1;
      return res;
    case val <= 0.79 && val > 0.65:
      res = 2;
      return res;
    case val <= 0.65 && val > 0.51:
      res = 3;
      return res;
    case val <= 0.5:
      res = 4;
      return res;
  }
  return val;
}

function evaluateExecutionFrequency(val) {
  var res;
  switch (true) {
    case val > 0:
      res = 2;
      return res;
    case val == 0:
      res = 1;
      return res;
  }
  return val;
}

function evaluateFrequencyOfReuse(val) {
  var res;
  switch (true) {
    case val > 9:
      res = 4;
      return res;
    case val <= 9 && val > 5:
      res = 3;
      return res;
    case val <= 5 && val >= 1:
      res = 2;
      return res;
    case val < 1:
      res = 1;
      return res;
  }
  return val;
}

function getWeightOfCriterion(weight) {
  if (Number.isInteger(weight) && weight > 0) {
    return weight;
  } else {
    return 1;
  }
}

function getValueOfKey(object, keyName) {
  for (const [key, value] of Object.entries(object)) {
    if (key == keyName) {
      if (
        [
          "customerWaitingTime",
          "automationRate",
          "executionFrequency",
          "frequencyOfReuse",
        ].includes(key)
      ) {
        if (key == "customerWaitingTime") {
          if (!isNaN(parseFloat(value))) {
            return evaluateCustomerWaitingTime(parseFloat(value));
          } else {
            return value;
          }
        } else if (key == "automationRate") {
          if (!isNaN(parseFloat(value))) {
            return evaluateAutomationRate(parseFloat(value));
          } else {
            return value;
          }
        } else if (key == "executionFrequency") {
          return evaluateExecutionFrequency(value);
        } else {
          return evaluateFrequencyOfReuse(value);
        }
      }
      return value;
    } else {
      continue;
    }
  }
}

function getPrioritzationValue(nameValueWeightScaleObject) {
  var relevantSum = 0;
  var baseline = 0;
  var priortizationPerc = 0;
  nameValueWeightScaleObject.forEach((element) => {
    if (!isNaN(parseFloat(element.value)) && element.value > 0) {
      relevantSum = relevantSum + element.value * element.weight;
      baseline = baseline + element.weight * element.scale;
    }
  });
  priortizationPerc = relevantSum / baseline;
  return Number(priortizationPerc*100).toFixed(2);
}

export default {
  name: "RPAPrioritizationListComp",
  components: { RPAActivityComp },
  data() {
    return {
      sortBy: "prioritizationValue",
      specifyWeightsDialog: false,
      sortDesc: true,
      isRobotDetails: false,
      activityId: "",
      selectedId: "",
      activeIndex: null,
      search: "",
      headers: [
        {
          text: "Robot",
          value: "topic",
          sortable: false,
          align: "center",
        },
        {
          text: "Frontend Stability",
          value: "frontendStability",
          sortable: false,
          align: "center",
        },
        {
          text: "End Of Life",
          value: "endOfLife",
          sortable: false,
          align: "center",
        },
        {
          text: "Automation Rate",
          value: "automationRate",
          sortable: false,
          align: "center",
        },
        {
          text: "Quality of Results",
          value: "qualityOfResults",
          sortable: false,
          align: "center",
        },
        {
          text: "Number of Bot Runners",
          value: "numberOfBotRunners",
          sortable: false,
          align: "center",
        },
        {
          text: "Execution Frequency",
          value: "executionFrequency",
          sortable: false,
          align: "center",
        },
        {
          text: "Number of Systems",
          value: "numberOfSystems",
          sortable: false,
          align: "center",
        },
        {
          text: "Business Impact",
          value: "businessImpact",
          sortable: false,
          align: "center",
        },
        {
          text: "Regulatory Compliance",
          value: "regulatoryCompliance",
          sortable: false,
          align: "center",
        },
        {
          text: "Frequency of Reuse",
          value: "frequencyOfReuse",
          sortable: false,
          align: "center",
        },
        {
          text: "Migration Urgency (in %)",
          value: "prioritizationValue",
          sortable: true,
          align: "center",
        },
      ],
      rpaPrioData: [],
      prioritizationList: [],
      weightsOfCriteria: [],
      rpaMetaDataArray: [],
      rpaMetaData: [],
    };
  },

  methods: {
    rowClick: function (item, row) {
      row.select(true);
      this.selectedId = item.topic;
      this.rpaMetaDataArray.forEach((o) => {
        if (o.topic == item.topic) {
          this.rpaMetaData = o;
        }
      });
      console.log(this.rpaMetaData);
      this.isRobotDetails = true;
    },
    specifyWeightsOfCriteriaUponCreated() {
      WeighingService.getWeightsByID().then((response) => {
        this.weightsOfCriteria = response.data;
        this.getRPAPrioData();
      });
    },
    specifyWeightsOfCriteria() {
      WeighingService.getWeightsByID().then((response) => {
        this.weightsOfCriteria = response.data;
        this.specifyWeightsDialog = true;
      });
    },
    save() {
      var correctWeightsOfCriteria = {};
      for (var k in this.weightsOfCriteria) {
        correctWeightsOfCriteria[k] = getWeightOfCriterion(
          this.weightsOfCriteria[k]
        );
      }
      this.weightsOfCriteria = correctWeightsOfCriteria;
      WeighingService.create(this.weightsOfCriteria).then(() => {
        this.getRPAPrioData();
        this.close();
      });
    },
    close() {
      this.specifyWeightsDialog = false;
      this.$nextTick(() => {
        this.weightsOfCriteria = Object.assign({}, this.weightsOfCriteria);
      });
    },
    getColor(prioritizationValue) {
      if (prioritizationValue > 75) return "red";
      else if (prioritizationValue > 35) return "orange";
      else return "green";
    },
    getRPAPrioData() {
      RPABotService.getRPABots().then((response) => {
        this.rpaPrioData = response.data;
        this.prioritizationList = [];
        this.rpaMetaDataArray = [];
        this.rpaPrioData.forEach((element) => {
          var to = getValueOfKey(element, "topic");
          var nameValueWeightScaleObject = [];
          var metaDataObj = [];
          var fe = {
            name: "frontendStability",
            value: getValueOfKey(element, "frontendStability"),
            weight: this.weightsOfCriteria["frontendStabilityWeight"],
            scale: 4,
          };
          var eol = {
            name: "endOfLife",
            value: getValueOfKey(element, "endOfLife"),
            weight: this.weightsOfCriteria["endOfLifeWeight"],
            scale: 4,
          };
          var at = {
            name: "automationRate",
            value: getValueOfKey(element, "automationRate"),
            weight: this.weightsOfCriteria["automationRateWeight"],
            scale: 4,
            orgVal: element.automationRate,
          };
          var qor = {
            name: "qualityOfResults",
            value: getValueOfKey(element, "qualityOfResults"),
            weight: this.weightsOfCriteria["qualityOfResultsWeight"],
            scale: 4,
          };
          var nobr = {
            name: "numberOfBotRunners",
            value: getValueOfKey(element, "numberOfBotRunners"),
            weight: this.weightsOfCriteria["numberOfBotRunnersWeight"],
            scale: 4,
          };
          var ef = {
            name: "executionFrequency",
            value: getValueOfKey(element, "executionFrequency"),
            weight: this.weightsOfCriteria["executionFrequencyWeight"],
            scale: 2,
            orgVal: element.executionFrequency,
          };
          var nos = {
            name: "numberOfSystems",
            value: getValueOfKey(element, "numberOfSystems"),
            weight: this.weightsOfCriteria["numberOfSystemsWeight"],
            scale: 2,
          };
          var bi = {
            name: "businessImpact",
            value: getValueOfKey(element, "businessImpact"),
            weight: this.weightsOfCriteria["businessImpactWeight"],
            scale: 4,
          };
          var roc = {
            name: "regulatoryCompliance",
            value: getValueOfKey(element, "regulatoryCompliance"),
            weight: this.weightsOfCriteria["regulatoryComplianceWeight"],
            scale: 2,
          };
          var fqre = {
            name: "frequencyOfReuse",
            value: getValueOfKey(element, "frequencyOfReuse"),
            weight: this.weightsOfCriteria["frequencyOfReuseWeight"],
            scale: 4,
            orgVal: element.frequencyOfReuse,
          };
          nameValueWeightScaleObject.push(
            fe,
            eol,
            at,
            qor,
            nobr,
            ef,
            nos,
            bi,
            roc,
            fqre
          );
          metaDataObj["activityId"] = element["activityId"];
          metaDataObj["activityIdUserTask"] = element["activityIdUserTask"];
          metaDataObj["totalCasesHandled"] = element["totalCasesHandled"];
          metaDataObj["casesHandledManually"] = element["casesHandledManually"];
          metaDataObj["topic"] = to;
          metaDataObj["definitionId"] = element["definitionId"];
          metaDataObj["metaData"] = {
            fe,
            eol,
            at,
            qor,
            nobr,
            ef,
            nos,
            bi,
            roc,
            fqre,
          };
          this.rpaMetaDataArray.push(metaDataObj);
          var pv = getPrioritzationValue(nameValueWeightScaleObject);
          this.prioritizationList.push({
            topic: to,
            frontendStability: fe.value,
            endOfLife: eol.value,
            automationRate: at.value,
            qualityOfResults: qor.value,
            numberOfBotRunners: nobr.value,
            executionFrequency: ef.value,
            numberOfSystems: nos.value,
            businessImpact: bi.value,
            regulatoryCompliance: roc.value,
            frequencyOfReuse: fqre.value,
            prioritizationValue: pv,
          });
          if (this.selectedId != "") {
            if (to == this.selectedId) {
              this.rpaMetaData = metaDataObj;
            }
          }
        });
      });
    },
  },
  created() {
    this.specifyWeightsOfCriteriaUponCreated();
  },
};
</script>

<style>
.rpa-prioritization-list-comp {
  margin-top: 20px;
}
tr.v-data-table__selected {
  background: #c6cadb !important;
}
</style>