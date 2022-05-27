'use strict';

var CamundaPropertiesProvider = require('bpmn-js-properties-panel/lib/provider/camunda/CamundaPropertiesProvider');
// var formHelper = require('bpmn-js-properties-panel/lib/helper/FormHelper');
var domify = require('min-dom').domify;
var swal = require('sweetalert');
// var copy = require('clipboard-copy');
var axios = require('axios');

const PROCESS_API_BASE_URL = 'http://localhost:8080/rest-api/'

// helper
function intercept(obj, fnName, cb) {
  const fn = obj[fnName];
  obj[fnName] = function () {
    return cb.call(this, fn, arguments);
  };
}

function isAnyAction(actions, action) {
  return actions.indexOf(action) > -1;
}

function removeOptions(selectElement) {
  var i, L = selectElement.options.length - 1;
  for(i = L; i >= 0; i--) {
     selectElement.remove(i);
  }
}

// main
function DocumentationRPAPluginProvider(injector, modeling, editorActions) {
  var camunda = new CamundaPropertiesProvider(...CamundaPropertiesProvider.$inject.map(dependency => injector.get(dependency)));
  // disable shortcuts
  intercept(editorActions, 'trigger', function (fn, args) {
    const action = args[0];
    if (isAnyAction([
      'removeSelection',
      'spaceTool',
      'lassoTool',
      'handTool',
      'directEditing'
    ], action)) {
      return;
    }
    return fn.apply(this, args);
  });

  this.getTabs = function (element) {
    var array = camunda.getTabs(element);
    var formIndex;
    var formsTab = array.filter(function (item, index) {
      if (item.id == 'element-template') {
        formIndex = index;
        return true;
      }
    });
    if (formsTab.length > 0) {
      var newFormsTab = this.getFormsTab(formsTab[0], modeling);
      array[formIndex] = newFormsTab;
    }
    return array;
  };
};

DocumentationRPAPluginProvider.prototype.getFormsTab = function (formsTab, modeling) {
  var self = this;
  if (formsTab.groups.length > 0 && formsTab.groups[0].entries.length > 0) {
    formsTab.groups[0].entries.splice(2, 0, {
      html: "<button id='specify-rpa-button' data-action='openPreview'>Specify RPA</button>",
      id: "form-fields-generate-button",
      openPreview: function (element, node) {
        self.generateHTML(element, modeling);
      }
    });
  }
  return formsTab;
};

DocumentationRPAPluginProvider.prototype.generateHTML = function (element, modeling) {
  var self = this;
  var tabPanel = `<div>
  <div class="tab">
    <button class="tablinks" data-id="chooseRobot">Choose Robot</button>
    <button class="tablinks" data-id="newRobot">Create Robot</button>
  </div>
  
  <div id="chooseRobot" class="tabcontent">

  <form style="display:flex;">
  <div style="width: 100%; margin-top:-20px;">
  <label for="selectExistingRobot" class="input-sm-custom">Select Existing Robot</label><br>
  <select id="select-existingRobot" name="selectExistingRobot" class="form-control"></select>
  </div>
  </form>

  <form style="display:flex">
  <div style="display:inline-block; width:75%">
  <p class="input-sm-custom" style="margin-bottom:-20px; margin-top: 7px; text-align:right" id="fetchExistingRobots"></p>
  </div>
  <div style="display:inline-block; width:25%">
  <button id="applySelectionForExistingRobot" class="publish-button" style="margin-bottom:-20px; margin-top:5px;" disabled>Select Robot</button>
  </div>
  </form>

  </div>

  <div id="newRobot" class="tabcontent">
  <form style="display:flex;">
  <div style="width: 100%; margin-top:-20px;">
  <label for="topicField" class="input-sm-custom">Topic</label><br><input type="text" class="form-control" name="topicField">
  </div>
  </form>

  <form style="display:flex; margin-top:5px;">
  <div style="width: 100%">
  <table width="100%">
  <tr style="background-color:lightgray; line-height:20px">
    <th style="width:30%">Criterion</th>
    <th style="width:15%"></th>
    <th style="width:40%; text-align:center">Assessment</th>
    <th style="width:15%"></th>
  </tr>
  <tr style="background-color:white; line-height:20px">
    <td colspan=2 style="font-style:italic">System Characteristics</td>
    <td>
    <ul class="likert">
      <li style="padding-right:15px">1</li>
      <li style="padding-right:15px">2</li>
      <li style="padding-right:15px">3</li>
      <li style="">4</li>
    </ul>
    </td>
    <td></td>
  </tr>
  <tr style="background-color:#F0F0F0">
    <td>Frontend Stability</td>
    <td>Stable</td>
    <td style="text-align:center">
      <ul class="likert">
        <li><input type="radio" name="feStability" value="1" /></li>
        <li><input type="radio" name="feStability" value="2" /></li>
        <li><input type="radio" name="feStability" value="3" /></li>
        <li><input type="radio" name="feStability" value="4" /></li>
      </ul>
    </td>
    <td>Changing</td>
  </tr>
  <tr style="background-color:#E0E0E0">
    <td>End of Life</td>
    <td>Permanent</td>
    <td style="text-align:center">
      <ul class="likert">
        <li><input type="radio" name="eol" value="1" /></li>
        <li><input type="radio" name="eol" value="2" /></li>
        <li><input type="radio" name="eol" value="3" /></li>
        <li><input type="radio" name="eol" value="4" /></li>
      </ul>
    </td>
    <td>Soon</td>
  </tr>
  <tr style="background-color:white; line-height:20px">
  <td colspan=2 style="font-style:italic">Robotic Performance</td>
  <td></td>
  <td></td>
  </tr>
  <tr style="background-color:#F0F0F0">
    <td>Quality of Results</td>
    <td>Adequate</td>
    <td style="text-align:center">
      <ul class="likert">
        <li><input type="radio" name="qualityOfResults" value="1" /></li>
        <li><input type="radio" name="qualityOfResults" value="2" /></li>
        <li><input type="radio" name="qualityOfResults" value="3" /></li>
        <li><input type="radio" name="qualityOfResults" value="4" /></li>
      </ul>
    </td>
    <td>Inadequate</td>
  </tr>
  <tr style="background-color:#E0E0E0">
    <td>Number Bot Runners</td>
    <td>Few</td>
    <td style="text-align:center">
      <ul class="likert">
        <li><input type="radio" name="numberOfBotRunners" value="1" /></li>
        <li><input type="radio" name="numberOfBotRunners" value="2" /></li>
        <li><input type="radio" name="numberOfBotRunners" value="3" /></li>
        <li><input type="radio" name="numberOfBotRunners" value="4" /></li>
      </ul>
    </td>
    <td>Many</td>
  </tr>
  <tr style="background-color:white; line-height:20px">
  <td colspan=2 style="font-style:italic">Task Characteristics</td>
  <td></td>
  <td></td>
  </tr>
  <tr style="background-color:#F0F0F0">
    <td>Business Impact</td>
    <td>Low</td>
    <td style="text-align:center">
      <ul class="likert">
        <li><input type="radio" name="businessImpact" value="1" /></li>
        <li><input type="radio" name="businessImpact" value="2" /></li>
        <li><input type="radio" name="businessImpact" value="3" /></li>
        <li><input type="radio" name="businessImpact" value="4" /></li>
      </ul>
    </td>
    <td>High</td>
  </tr>
  </table>
  </form>

  <form style="display:flex; margin-top:5px;">
  <div style="display:inline-block; width:50%">
  <label for="numberOfSystems" class="input-sm-custom">Multi-System Robot?</label><br>
  <select id="select-numOfSys" name="numberOfSystems" class="form-control">
    <option value="2">No</option> 
    <option value="1">Yes</option>                  
  </select>
  </div>
  <div style="width:10px; display:inline-block;"></div>
  <div style="display:inline-block; width:50%;">
  <label for="regulatoryCompliance" class="input-sm-custom">Regulatory Compliance?</label><br>
  <select id="select-regCompl" name="regulatoryCompliance" class="form-control">
    <option value="1">No</option> 
    <option value="2">Yes</option>                  
  </select>
  </div>
  </form>

  <form style="display:flex">
  <div style="display:inline-block; width:75%">
  <p class="input-sm-custom" style="margin-bottom:-20px; margin-top: 7px; text-align:right" id="uploadSuccessfulOrNot"></p>
  </div>
  <div style="display:inline-block; width:25%">
  <button id="publishspecs" class="publish-button" style="margin-bottom:-20px; margin-top:5px;">Publish</button>
  </div>
  </form>

  </div>
  </div>`;
  var domHtml = domify(tabPanel);
  swal({
    text: "RPA Bot Specification",
    content: domHtml
  });
  var tablinks = document.querySelectorAll(".tablinks");
  
  tablinks.forEach(function (tablink) {
    tablink.addEventListener("click", function (event) {
      var id = event.target.getAttribute("data-id");
      self.openTab(event, id);
    });
  });

  var applySelectionForExistingRobotButton = document.querySelector("#applySelectionForExistingRobot");
  var selectFieldExistingRobots = document.getElementById('select-existingRobot');
  const getRobots = async() => {
    axios.get(PROCESS_API_BASE_URL + "rpa/getBots").then((response) => {
      console.log(response);
      const robotOptions = response.data;
      document.getElementById("fetchExistingRobots").innerHTML = "";
      if (robotOptions.length == 0) {
        document.getElementById("fetchExistingRobots").innerHTML = "No Robots exist yet.";
      } else {
      robotOptions.forEach(option => {
        const newOption = document.createElement("option");
        newOption.value = option.topic;
        newOption.text = option.topic;
        selectFieldExistingRobots.appendChild(newOption);
      });
      applySelectionForExistingRobotButton.removeAttribute('disabled');
    }
    }).catch(function () {
      document.getElementById("fetchExistingRobots").innerHTML = "Failed to fetch existing Robots.";
    });
  }
  tablinks[0].addEventListener("click", function() {
    if (selectFieldExistingRobots.options.length > 0) {
      removeOptions(selectFieldExistingRobots);
    }
    applySelectionForExistingRobotButton.setAttribute('disabled', 'disabled');
    getRobots();
  });
  tablinks[0].click();

  applySelectionForExistingRobotButton.addEventListener('click', function () {
    var robotChosen = document.getElementById('select-existingRobot').value;
    modeling.updateProperties(element, {
      topic: robotChosen,
    });
  });

  var publishbutton = document.querySelector("#publishspecs");
  publishbutton.addEventListener('click', function () {
    while(true){
    try {
      var topicSubscription = document.querySelector('input[name="topicField"]').value;
      var frontendStability = document.querySelector('input[name="feStability"]:checked').value;
      var endOfLife = document.querySelector('input[name="eol"]:checked').value;
      var qualityOfResults = document.querySelector('input[name="qualityOfResults"]:checked').value;
      var numberOfBotRunners = document.querySelector('input[name="numberOfBotRunners"]:checked').value;
      var businessImpact = document.querySelector('input[name="businessImpact"]:checked').value;
      var numberOfSystems = document.getElementById('select-numOfSys').value;
      var regulatoryCompliance = document.getElementById('select-regCompl').value;
    }
    catch(err) {
      document.getElementById("uploadSuccessfulOrNot").innerHTML = "RPA Documentation failed to be uploaded.";
      break;
    }
    var jsonRPAJsonObject = {
      "topic": topicSubscription,
      "frontendStability": frontendStability,
      "endOfLife": endOfLife,
      "qualityOfResults": qualityOfResults,
      "numberOfBotRunners": numberOfBotRunners,
      "businessImpact": businessImpact,
      "numberOfSystems": numberOfSystems,
      "regulatoryCompliance": regulatoryCompliance
    }

    axios.post(PROCESS_API_BASE_URL + "rpa/create",
      JSON.stringify(jsonRPAJsonObject),
      {
        headers: {
          'Content-Type': 'application/json'
        }
      }
    ).then(function () {
      console.log("RPA Documentation uploaded.");
      document.getElementById("uploadSuccessfulOrNot").innerHTML = "RPA Documentation uploaded.";
      modeling.updateProperties(element, {
        topic: topicSubscription,
      });
    })
      .catch(function () {
        console.log("RPA Documentation failed to be uploaded.");
        document.getElementById("uploadSuccessfulOrNot").innerHTML = "RPA Documentation failed to be uploaded.";
      })
    break;
    }
  });
}

DocumentationRPAPluginProvider.prototype.openTab = function (evt, tabName) {

  // Declare all variables
  var i, tabcontent, tablinks;

  // Get all elements with class="tabcontent" and hide them
  tabcontent = document.getElementsByClassName("tabcontent");
  for (i = 0; i < tabcontent.length; i++) {
    tabcontent[i].style.display = "none";
  }

  // Get all elements with class="tablinks" and remove the class "active"
  tablinks = document.getElementsByClassName("tablinks");
  for (i = 0; i < tablinks.length; i++) {
    tablinks[i].className = tablinks[i].className.replace(" active", "");
  }

  // Show the current tab, and add an "active" class to the button that opened the tab
  document.getElementById(tabName).style.display = "block";
  evt.currentTarget.className += " active";
}

DocumentationRPAPlugin.$inject = ['injector', 'modeling', 'editorActions'];

function DocumentationRPAPlugin() {

};

module.exports = {
  __init__: ['documentationRPAPlugin'],
  propertiesProvider: ['type', DocumentationRPAPluginProvider],
  documentationRPAPlugin: ['type', DocumentationRPAPlugin]
};
