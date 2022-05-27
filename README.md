# RPA-API Platform

The RPA-API platform consists of five software artefacts in total. Please find them listed below along with a short description.

| Artefact | Description | Type of Application |
| ----------- | ----------- | ----------- |
| [Camunda Modeler Plugin](camunda-modeler-plugin-rpa-documentation) | Plugin for the Camunda Modeler that allows migration-relevant information to be captured | Plugin written in Javascript |
| [Exemplary Legacy App](exemplarly-legacy-app) | Exemplary legacy application for the RPA bot that finds the CPL-ID | Single page application realized with Vue.js |
| [Platform Core](platform-core) | Camunda Engine with custom endpoints and extended business logic | Spring boot application | 
| [Platform Integration Service](platform-integration-service) | Bridging component to connect to UiPath Orchestrator | Spring boot application |
| [Platform View](platform-view) | Migration-supporting frontend that allows to prioritize and migrate robots on the basis of data mappings | Single page application realized with Vue.js |

## Camunda Modeler Plugin
The [Camunda Modeler](https://github.com/camunda/camunda-modeler) is extended to configure external service tasks as to being robotic-automated.
### Prerequisite
Prior to installing the plugin, make sure the following domain-specific diagram element is included as `rpa-task.json` in `...\Camunda Modeler\resources\element-templates`.

```
[
    {
    "$schema": "https://unpkg.com/@camunda/element-templates-json-schema@0.4.0/resources/schema.json",
    "name": "RPA Task",
    "id": "com.camunda.example.RPATask",
    "appliesTo": [
      "bpmn:ServiceTask"
    ],
    "properties": [
      {
		"label": "Implementation Type",
        "binding": {
          "name": "camunda:type",
          "type": "property"
        },
        "type": "String",
		"editable": false,
        "value": "external"
      },
      {
        "binding": {
          "name": "camunda:topic",
          "type": "property"
        },
        "label": "Topic",
		"editable": false,
        "type": "String",
        "value": ""
      }
    ]
  }
]
``` 
### Installation
Go to `...\Camunda Modeler\resources\plugins` and put the plugin in the directory. Finally, install all necessary module dependencies with the help of the node package manager.
```
npm install
```
Finally, it needs to be bundled so that the plugin can be executed in the Camunda Modeler.
```
npm run client
```

## Exemplary Legacy App
The exemplary legacy application represents an artificial greenscreen user interface so that the RPA bot for the search of the CPL-ID can exemplary perform its operation.

### Installation
Go to `...\exemplarly-legacy-app` and install the command line interface for Vue.js development.
```
npm install -g @vue/cli
```
Next, install all dependencies in the following way.
```
npm install
```
Finally, start the application.
```
npm run serve
```
The exemplary legacy app can now be reached from the local host via [http://localhost:3000/](http://localhost:3000/).

## Platform Core
The platform core encompasses the Camunda Engine along with repositories for the execution logs, the process models, and the migration-relevant criteria that are recorded during modeling with the help of the plugin from above.

### Installation
Navigate to the root of the project via the command line and run the application using maven.
```
mvn spring-boot:run
```
The CLI will run the application on port 8080. Alternatively, it can be directly started from the IDE.

## Platform Integration Service
The platform integration service realizes the bridge between the Camunda Engine and the [UiPath Orchestrator](https://www.uipath.com/de/product/orchestrator). It allows calling RPA bots from BPMN process models that are deployed to the BPMS. With the help of the plugin for the Camunda Modeler, these robots become orchestrated as external tasks along the processes from start to finish.

### Installation
Navigate to the root of the project via the command line and run the application using maven.
```
mvn spring-boot:run
```
The CLI will run the application on port 8090. Alternatively, it can be directly started from the IDE.

## Platform View
The platform view provides the migration-supporting frontend so that the RPA bots can be listed in the order of their replacement. Furthermore, it allows data mappings to be stored in the integration service's database so that the migration from RPA to API can happen just by switching the topic names in the process models.

### Installation
Go to `...\rpa-api-platform\platform-view` and install, if not already done so, the CLI for Vue.js development.
```
npm install -g @vue/cli
```
Next, install all dependencies in the following way.
```
npm install
```
Finally, start a development server.
```
npm run serve
```
The migration-supporting frontend can now be reached from the local host via a port that gets chosen during the application's startup automatically, e.g., [http://localhost:8081/](http://localhost:8081/).
