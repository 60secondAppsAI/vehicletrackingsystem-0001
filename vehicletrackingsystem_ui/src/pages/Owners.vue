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
            <owner-table
            v-if="owners && owners.length > 0"
				:title="table1.title"
				:sub-title="table1.subTitle"
				:owners="owners"
				:totalElements="totalElements"
				:page="page"
				:columns="table1.columns"
 				@get-all-owners="getAllOwners"
             >

            </owner-table>
          </div>
        </card>
      </div>

    </div>
  </div>
</template>
<script>
import { Card } from "@/components/index";

import OwnerTable from "@/components/OwnerTable";
import OwnerService from "../services/OwnerService";

const tableColumns = [];
const tableData = [
];

export default {
  components: {
    Card,
    OwnerTable,
  },
  data() {
    return {
      owners: [],
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
    async getAllOwners(sortBy='ownerId',sortOrder='true',page="0",size="50") {
      try {
        try {
			const searchDTO = {
				sortBy: sortBy, 
				sortOrder: sortOrder, 
				searchQuery: this.searchQuery,
				page: page, 
				size: size
			};
	        let response = await OwnerService.getAllOwners(searchDTO);
	        if (!response.data.empty) {
		        if (response.data.owners.length) {
					this.owners = response.data.owners;
				}
      			this.totalElements = response.data.totalElements;
      			this.page = response.data.page;
	        }
        } catch (error) {
          console.error("Error fetching owners:", error);
        }
        
      } catch (error) {
        console.error("Error fetching owner details:", error);
      }
    },
  },
  mounted() {
    this.getAllOwners();
  },
  created() {
    this.$root.$on('searchQueryForOwnersChanged', (searchQuery) => {
    	this.searchQuery = searchQuery;
    	this.getAllOwners();
    })
  }
};
</script>
<style></style>
