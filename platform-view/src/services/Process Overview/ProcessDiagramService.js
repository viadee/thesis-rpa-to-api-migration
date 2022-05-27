import axios from 'axios'

const PROCESS_API_BASE_URL = 'http://localhost:8080/rest-api/'
class ProcessDiagramService {
    getProcessDiagram(id) {
        return axios.get(PROCESS_API_BASE_URL + "process-definition/" + id + "/xml");
    }

    getProcessActivtiyInf(id) {
        return axios.get(PROCESS_API_BASE_URL + "process-definition/" + id + "/statistics");
    }

    getAllVersionDataOfProcessKey(key) {
        return axios.get(PROCESS_API_BASE_URL + "process-definition?key=" + key);
    }

    getAllProcessInstances(id) {
        return axios.get(PROCESS_API_BASE_URL + "instances/getInstanceData?processDefinitionId=" + id);
    }
}

export default new ProcessDiagramService();