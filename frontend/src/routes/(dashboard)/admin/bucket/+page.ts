import { browser } from '$app/environment';
import { redirect } from '@sveltejs/kit';
import type { PageLoad } from '../$types';

export const load: PageLoad = async ({ fetch }) => {
	// try {
	// 	if (browser) {
	// 		const token = localStorage.getItem('token');

	// 		const response = await fetch('/api/admin/movie', {
	// 			headers: {
	// 				'Content-Type': 'application/json',
	// 				Authorization: `Bearer ${token}`
	// 			}
	// 		});
	// 		if (!response.ok) {
	// 			throw new Error('Failed to fetch admin movie data');
	// 		}
	// 		const movies = await response.json();

	// 		return {
	// 			movies
	// 		};
	// 	}
	// } catch (error) {
	// 	console.error('Error fetching admin movie data:', error);
	// }
	// return {
	// 	movies: []
	// };
	if (!browser) return { movies: [] };

	const token = localStorage.getItem('token');

	const response = await fetch('/api/admin/movie', {
		headers: {
			'Content-Type': 'application/json',
			Authorization: `Bearer ${token}`
		}
	});

	if (!response.ok) {
		console.error('Failed to fetch admin movie data');
		return { movies: [] }; // or throw an error if you want the error page
	}

	const movies = await response.json();

	if (!movies || movies.length === 0) {
		// This must escape the load function so SvelteKit will redirect
		redirect(302, '/admin');
	}

	return { movies };
};
