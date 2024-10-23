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
            <position-table
            v-if="positions && positions.length > 0"
				:title="table1.title"
				:sub-title="table1.subTitle"
				:positions="positions"
				:totalElements="totalElements"
				:page="page"
				:columns="table1.columns"
 				@get-all-positions="getAllPositions"
             >

            </position-table>
          </div>
        </card>
      </div>

    </div>
  </div>
</template>
<script>
import { Card } from "@/components/index";

import PositionTable from "@/components/PositionTable";
import PositionService from "../services/PositionService";

const tableColumns = [];
const tableData = [
];

export default {
  components: {
    Card,
    PositionTable,
  },
  data() {
    return {
      positions: [],
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
    async getAllPositions(sortBy='positionId',sortOrder='true',page="0",size="50") {
      try {
        try {
			const searchDTO = {
				sortBy: sortBy, 
				sortOrder: sortOrder, 
				searchQuery: this.searchQuery,
				page: page, 
				size: size
			};
	        let response = await PositionService.getAllPositions(searchDTO);
	        if (!response.data.empty) {
		        if (response.data.positions.length) {
					this.positions = response.data.positions;
				}
      			this.totalElements = response.data.totalElements;
      			this.page = response.data.page;
	        }
        } catch (error) {
          console.error("Error fetching positions:", error);
        }
        
      } catch (error) {
        console.error("Error fetching position details:", error);
      }
    },
  },
  mounted() {
    this.getAllPositions();
  },
  created() {
    this.$root.$on('searchQueryForPositionsChanged', (searchQuery) => {
    	this.searchQuery = searchQuery;
    	this.getAllPositions();
    })
  }
};
</script>
<style></style>
