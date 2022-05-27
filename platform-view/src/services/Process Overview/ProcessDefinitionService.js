import axios from 'axios'

const PROCESS_API_BASE_URL = 'http://localhost:8080/rest-api/';

class ProcessDefinitionService {
    getProcessDefinitions() {
        return axios.get(PROCESS_API_BASE_URL + "processes/getProcesses");
    }
    uploadProcessDefinition(formData) {
        return axios.post(PROCESS_API_BASE_URL + "deployment/create",
            formData,
            {
                headers: {
                    'Content-Type': 'multipart/form-data'
                }
            }).then(function () {
                console.log("File uploaded.")
            })
            .catch(function () {
                console.log("File upload failed.")
            })
    }
    deleteProcessDefinitionsByKey(processKey){
        return axios.delete(PROCESS_API_BASE_URL + "processes/deleteProcessDefinitionsByKey?processKey=" + processKey);
    }
    getXMLofProcessDefinition(processKey){
        return axios.get(PROCESS_API_BASE_URL + "process-definition/key/" + processKey + "/xml");
    }
    postProcessInstance(data, id) {
        return axios.post(PROCESS_API_BASE_URL + "process-definition/" + id +"/start",
            data,
            {
                headers: {
                    'Content-Type': 'application/json'
                }
            }
        ).then(function () {
            console.log("Process Instance uploaded.")
        }).catch(function () {
            console.log("Process Instance failed to be uploaded.")
        })
    }
}

export default new ProcessDefinitionService();