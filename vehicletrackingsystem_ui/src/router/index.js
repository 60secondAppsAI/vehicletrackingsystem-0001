import Vue from 'vue'
import VueRouter from 'vue-router'
import Vehicles from  '@/pages/Vehicles.vue';
import VehicleDetail from  '@/pages/VehicleDetail.vue';
import Owners from  '@/pages/Owners.vue';
import OwnerDetail from  '@/pages/OwnerDetail.vue';
import Positions from  '@/pages/Positions.vue';
import PositionDetail from  '@/pages/PositionDetail.vue';
import Fleets from  '@/pages/Fleets.vue';
import FleetDetail from  '@/pages/FleetDetail.vue';

Vue.use(VueRouter)

let routes = [
	{
		// will match everything
		path: '*',
		component: () => import('../views/404.vue'),
	},
	{
		path: '/',
		name: 'Home',
			redirect: '/vehicles',
					},
	{
		path: '/dashboard',
		name: 'Dashboard',
		layout: "dashboard",
		// route level code-splitting
		// this generates a separate chunk (about.[hash].js) for this route
		// which is lazy-loaded when the route is visited.
		component: () => import(/* webpackChunkName: "dashboard" */ '../views/Dashboard.vue'),
	},
	{
		path: '/layout',
		name: 'Layout',
		layout: "dashboard",
		component: () => import('../views/Layout.vue'),
	},
	{
		path: '/vehicles',
		name: 'Vehicles',
		layout: "dashboard",
		component: Vehicles,
	},
	{
	    path: '/vehicle/:vehicleId', 
	    name: 'VehicleDetail',
		layout: "dashboard",
	    component: VehicleDetail,
	    props: true // Pass route params as props to the component
  	},
	{
		path: '/owners',
		name: 'Owners',
		layout: "dashboard",
		component: Owners,
	},
	{
	    path: '/owner/:ownerId', 
	    name: 'OwnerDetail',
		layout: "dashboard",
	    component: OwnerDetail,
	    props: true // Pass route params as props to the component
  	},
	{
		path: '/positions',
		name: 'Positions',
		layout: "dashboard",
		component: Positions,
	},
	{
	    path: '/position/:positionId', 
	    name: 'PositionDetail',
		layout: "dashboard",
	    component: PositionDetail,
	    props: true // Pass route params as props to the component
  	},
	{
		path: '/fleets',
		name: 'Fleets',
		layout: "dashboard",
		component: Fleets,
	},
	{
	    path: '/fleet/:fleetId', 
	    name: 'FleetDetail',
		layout: "dashboard",
	    component: FleetDetail,
	    props: true // Pass route params as props to the component
  	},
	{
		path: '/requests/quickadd',
		name: 'QuickAdd',
		layout: "dashboard",
		meta: {
			title: 'quickadd',
			sidebarMap: ['applications'],
			breadcrumbs: ['Requests', 'QuickAdd'],
		},
		component: () => import('../pages/QuickAdd.vue'),
	},
	{
		path: '/tables',
		name: 'Tables',
		layout: "dashboard",
		component: () => import('../views/Tables.vue'),
	},
	{
		path: '/billing',
		name: 'Billing',
		layout: "dashboard",
		component: () => import('../views/Billing.vue'),
	},
	{
		path: '/rtl',
		name: 'RTL',
		layout: "dashboard-rtl",
		meta: {
			layoutClass: 'dashboard-rtl',
		},
		component: () => import('../views/RTL.vue'),
	},
	{
		path: '/Profile',
		name: 'Profile',
		layout: "dashboard",
		meta: {
			layoutClass: 'layout-profile',
		},
		component: () => import('../views/Profile.vue'),
	},
	{
		path: '/sign-in',
		name: 'Sign-In',
		component: () => import('../views/Sign-In.vue'),
	},
	{
		path: '/sign-up',
		name: 'Sign-Up',
		meta: {
			layoutClass: 'layout-sign-up',
		},
		component: () => import('../views/Sign-Up.vue'),
	},
]

// Adding layout property from each route to the meta
// object so it can be accessed later.
function addLayoutToRoute( route, parentLayout = "default" )
{
	route.meta = route.meta || {} ;
	route.meta.layout = route.layout || parentLayout ;
	
	if( route.children )
	{
		route.children = route.children.map( ( childRoute ) => addLayoutToRoute( childRoute, route.meta.layout ) ) ;
	}
	return route ;
}

routes = routes.map( ( route ) => addLayoutToRoute( route ) ) ;

const router = new VueRouter({
	mode: 'hash',
	base: process.env.BASE_URL,
	routes,
	scrollBehavior (to, from, savedPosition) {
		if ( to.hash ) {
			return {
				selector: to.hash,
				behavior: 'smooth',
			}
		}
		return {
			x: 0,
			y: 0,
			behavior: 'smooth',
		}
	}
})

export default router
