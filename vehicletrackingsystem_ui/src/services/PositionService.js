import http from "../http-common";

class PositionService {
  getAllPositions(searchDTO) {
    console.log(searchDTO)
    return this.getRequest(`/position/positions`, searchDTO);
  }

  get(positionId) {
    return this.getRequest(`/position/${positionId}`, null);
  }

  findByField(matchData) {
    return this.getRequest(`/position?field=${matchData}`, null);
  }

  addPosition(data) {
    return http.post("/position/addPosition", data);
  }

  update(data) {
  	return http.post("/position/updatePosition", data);
  }
  
  uploadImage(data,positionId) {
  	return http.postForm("/position/uploadImage/"+positionId, data);
  }




	postRequest(url, data) {
		return http.post(url, data);
      };

	getRequest(url, params) {
        return http.get(url, {
        	params: params
        });
    };

}

export default new PositionService();
