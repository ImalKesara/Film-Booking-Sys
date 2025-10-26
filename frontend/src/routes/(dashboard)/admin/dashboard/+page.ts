import type { PageLoad } from '../$types';

export const load: PageLoad = async ({ fetch }) => {
	const token = localStorage.getItem('token');
	const response = await fetch('/api/admin/sales-summary', {
		headers: {
			'Content-Type': 'application/json',
			Authorization: `Bearer ${token}`
		}
	});

	const data = await response.json();

	return { summaries: data };
};
