import axios from 'axios'

const PROCESS_API_BASE_URL = 'http://localhost:8080/rest-api/'
class GetProcessDiagramService {
    getProcessDiagram(id) {
        return axios.get(PROCESS_API_BASE_URL + "process-definition/" + id + "/xml");
    }
}

export default new GetProcessDiagramService();