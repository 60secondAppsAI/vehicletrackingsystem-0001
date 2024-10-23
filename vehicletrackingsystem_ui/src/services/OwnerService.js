import http from "../http-common";

class OwnerService {
  getAllOwners(searchDTO) {
    console.log(searchDTO)
    return this.getRequest(`/owner/owners`, searchDTO);
  }

  get(ownerId) {
    return this.getRequest(`/owner/${ownerId}`, null);
  }

  findByField(matchData) {
    return this.getRequest(`/owner?field=${matchData}`, null);
  }

  addOwner(data) {
    return http.post("/owner/addOwner", data);
  }

  update(data) {
  	return http.post("/owner/updateOwner", data);
  }
  
  uploadImage(data,ownerId) {
  	return http.postForm("/owner/uploadImage/"+ownerId, data);
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

export default new OwnerService();
