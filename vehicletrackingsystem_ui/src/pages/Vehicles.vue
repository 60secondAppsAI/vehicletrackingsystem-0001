<template>
  <div class="content">
    <div class="row">
      <div class="col-12">
        <card class="card-plain">
          <!-- <template slot="header">
            <h4 class="card-title">Table on Plain Background</h4>
            <p class="category">Here is a subtitle for this table</p>
          </template>-->
          <div class="table-full-width text-left">
            <vehicle-table
            v-if="vehicles && vehicles.length > 0"
				:title="table1.title"
				:sub-title="table1.subTitle"
				:vehicles="vehicles"
				:totalElements="totalElements"
				:page="page"
				:columns="table1.columns"
 				@get-all-vehicles="getAllVehicles"
             >

            </vehicle-table>
          </div>
        </card>
      </div>

    </div>
  </div>
</template>
<script>
import { Card } from "@/components/index";

import VehicleTable from "@/components/VehicleTable";
import VehicleService from "../services/VehicleService";

const tableColumns = [];
const tableData = [
];

export default {
  components: {
    Card,
    VehicleTable,
  },
  data() {
    return {
      vehicles: [],
	  totalElements: 0,
      page: 0,
      searchQuery: '',     
      table1: {
        title: "Simple Table",
        columns: [...tableColumns],
        data: [...tableData],
      },
    };
  },
  methods: {
    async getAllVehicles(sortBy='vehicleId',sortOrder='true',page="0",size="50") {
      try {
        try {
			const searchDTO = {
				sortBy: sortBy, 
				sortOrder: sortOrder, 
				searchQuery: this.searchQuery,
				page: page, 
				size: size
			};
	        let response = await VehicleService.getAllVehicles(searchDTO);
	        if (!response.data.empty) {
		        if (response.data.vehicles.length) {
					this.vehicles = response.data.vehicles;
				}
      			this.totalElements = response.data.totalElements;
      			this.page = response.data.page;
	        }
        } catch (error) {
          console.error("Error fetching vehicles:", error);
        }
        
      } catch (error) {
        console.error("Error fetching vehicle details:", error);
      }
    },
  },
  mounted() {
    this.getAllVehicles();
  },
  created() {
    this.$root.$on('searchQueryForVehiclesChanged', (searchQuery) => {
    	this.searchQuery = searchQuery;
    	this.getAllVehicles();
    })
  }
};
</script>
<style></style>
