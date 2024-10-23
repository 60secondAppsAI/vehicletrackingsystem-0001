import http from "../http-common";

class VehicleService {
  getAllVehicles(searchDTO) {
    console.log(searchDTO)
    return this.getRequest(`/vehicle/vehicles`, searchDTO);
  }

  get(vehicleId) {
    return this.getRequest(`/vehicle/${vehicleId}`, null);
  }

  findByField(matchData) {
    return this.getRequest(`/vehicle?field=${matchData}`, null);
  }

  addVehicle(data) {
    return http.post("/vehicle/addVehicle", data);
  }

  update(data) {
  	return http.post("/vehicle/updateVehicle", data);
  }
  
  uploadImage(data,vehicleId) {
  	return http.postForm("/vehicle/uploadImage/"+vehicleId, data);
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

export default new VehicleService();
