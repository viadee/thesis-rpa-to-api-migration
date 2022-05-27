import axios from 'axios'

const PROCESS_API_BASE_URL = 'http://localhost:8080/rest-api/';

class WeighingService {
    getAllWeights() {
        return axios.get(PROCESS_API_BASE_URL + "criteria/getAllWeights");
    }
    create(weighingCriteria) {
        return axios.post(PROCESS_API_BASE_URL + "criteria/create",
        weighingCriteria,
        {
            headers: {
                'Content-Type': 'application/json'
            }
        }).then(function () {
            console.log("Criteria uploaded.")
        })
        .catch(function () {
            console.log("Criteria upload failed.")
        })
    }
    getWeightsByID() {
        return axios.get(PROCESS_API_BASE_URL + "criteria/getWeightsByID?id=1");
    }
}

export default new WeighingService();