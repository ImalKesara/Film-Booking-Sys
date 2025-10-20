import { browser } from '$app/environment';
import type { PageLoad } from '../$types';

export const load: PageLoad = async ({ fetch }) => {
	try {
		if (browser) {
			const token = localStorage.getItem('token');

			const response = await fetch('/api/admin/movie', {
				headers: {
					'Content-Type': 'application/json',
					Authorization: `Bearer ${token}`
				}
			});
			if (!response.ok) {
				throw new Error('Failed to fetch admin movie data');
			}
			const movies = await response.json();
			return {
				movies
			};
		}
	} catch (error) {
		console.error('Error fetching admin movie data:', error);
	}
	return {
		movies: []
	};
};
