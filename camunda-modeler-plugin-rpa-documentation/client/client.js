var registerBpmnJSPlugin = require('camunda-modeler-plugin-helpers').registerBpmnJSPlugin;

var DocumentationRPAPluginProvider = require('./DocumentationRPAPluginProvider');
registerBpmnJSPlugin(DocumentationRPAPluginProvider);