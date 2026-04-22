// import type { PageLoad } from '../$types';

// export const load: PageLoad = async ({ fetch }) => {
// 	const token = localStorage.getItem('token');
// 	const option = {
// 		headers: {
// 			'Content-Type': 'application/json',
// 			Authorization: `Bearer ${token}`
// 		}
// 	};
// 	const [sales, latest_revenue, all_revenue, total_movies, location] = await Promise.all([
// 		fetch('/api/admin/sales-summary', option),
// 		fetch('/api/daily-revenue/latest', option),
// 		fetch('/api/daily-revenue/all', option),
// 		fetch('/api/admin/movie', option),
// 		fetch('/api/admin/location', option)
// 	]);

// 	const data = await sales.json();
// 	const latestRevenue = await latest_revenue.json();
// 	const allRevenue = await all_revenue.json();
// 	const totalMovies = await total_movies.json();
// 	const locationData = await location.json();

// 	return { summaries: data, latestRevenue, allRevenue, totalMovies, locationData };
// };

import type { PageLoad } from './$types';

// Helper function to safely parse JSON and prevent crashes
async function safeFetchJson(response: Response, fallback: any) {
	// If the backend threw an error (404, 500, 403, etc.), return the fallback
	if (!response.ok) {
		console.warn(`Warning: Fetch failed for ${response.url} with status ${response.status}`);
		return fallback;
	}

	// Read as text first. If it's empty, return the fallback instead of crashing
	const text = await response.text();
	return text ? JSON.parse(text) : fallback;
}

export const load: PageLoad = async ({ fetch }) => {
	// SvelteKit Load Safety: Ensure localStorage is only accessed in the browser
	// If this runs on the server during initial load, localStorage is undefined and will crash
	const token = typeof window !== 'undefined' ? localStorage.getItem('token') : null;

	const option = {
		headers: {
			'Content-Type': 'application/json',
			// Only attach the Bearer token if it exists
			...(token && { Authorization: `Bearer ${token}` })
		}
	};

	// Fetch all endpoints simultaneously
	const [sales, latest_revenue, all_revenue, total_movies, location] = await Promise.all([
		fetch('/api/admin/sales-summary', option),
		fetch('/api/daily-revenue/latest', option),
		fetch('/api/daily-revenue/all', option),
		fetch('/api/admin/movie', option),
		fetch('/api/admin/location', option)
	]);

	// Safely parse each response.
	// Notice the second argument is the "fallback" data shape your UI expects if the DB is empty
	const data = await safeFetchJson(sales, []);
	const latestRevenue = await safeFetchJson(latest_revenue, { totalRevenue: 0 }); // Object expected by UI
	const allRevenue = await safeFetchJson(all_revenue, []);
	const totalMovies = await safeFetchJson(total_movies, []);
	const locationData = await safeFetchJson(location, []);

	return {
		summaries: data,
		latestRevenue,
		allRevenue,
		totalMovies,
		locationData
	};
};
