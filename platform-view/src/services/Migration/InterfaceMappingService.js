import axios from 'axios'

const PROCESS_API_BASE_URL = 'http://localhost:8090/';

class InterfaceMappingService {
    getTopics() {
        return axios.get(PROCESS_API_BASE_URL + "api-topics/getTopics");
    }
    getMappingDataOfTopics() {
        return axios.get(PROCESS_API_BASE_URL + "var-mappings/getMappings");
    }
    deleteMapppingDataAndTopic(topic) {
        return axios.get(PROCESS_API_BASE_URL + "/var-mappings/delete?topic=" + topic);
    }
    getMappingDataOfTopic(topic){
        return axios.get(PROCESS_API_BASE_URL + "var-mappings/getMapping?topic=" + topic);
    }
    createMappingForTopic(mapping){
        return axios.post(PROCESS_API_BASE_URL + "var-mappings/create",
            mapping,
            {
                headers: {
                    'Content-Type': 'application/json'
                }
            }
        ).then(function () {
            console.log("Mapping created for:");
            console.log(mapping);
        }).catch(function () {
            console.log("Mapping could not be created for:");
            console.log(mapping);
        })
    }
    restartApiWorker(){
        return axios.get(PROCESS_API_BASE_URL + "/api-worker/restart");
    }
}

export default new InterfaceMappingService();