import type { PageLoad } from '../$types';

export const load: PageLoad = async ({ fetch }) => {
	const token = localStorage.getItem('token');
	const option = {
		headers: {
			'Content-Type': 'application/json',
			Authorization: `Bearer ${token}`
		}
	};
	const [sales, latest_revenue, all_revenue] = await Promise.all([
		fetch('/api/admin/sales-summary', option),
		fetch('/api/daily-revenue/latest', option),
		fetch('/api/daily-revenue/all', option)
	]);

	const data = await sales.json();
	const latestRevenue = await latest_revenue.json();
	const allRevenue = await all_revenue.json();

	return { summaries: data, latestRevenue, allRevenue };
};
