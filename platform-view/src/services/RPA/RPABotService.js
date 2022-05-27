import axios from 'axios'

const PROCESS_API_BASE_URL = 'http://localhost:8080/rest-api/';

class RPABotService {
    getRPABots() {
        return axios.get(PROCESS_API_BASE_URL + "rpa/getRPATasksInModels");
    }
}

export default new RPABotService();