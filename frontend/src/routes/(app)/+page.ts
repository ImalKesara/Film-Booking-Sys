import type { PageLoad } from './$types';

export const load: PageLoad = async ({fetch}) => {
	try {
		const response = await fetch('/api/user/movie', {
			headers: {
				'Content-Type': 'application/json'
			}
		});

		if (!response.ok) throw new Error('Failed to fetch movies');

		const movies = await response.json();

		return {
			movies
		};
	} catch (error) {
		console.error('Error fetching movies:', error);
		return {
			movies: []
		};
	}
};
