import type { PageLoad } from './$types';
import { PUBLIC_TMDB_API_KEY } from '$env/static/public';

export const load: PageLoad = async ({ fetch }) => {
	try {
		const options = {
			method: 'GET',
			headers: {
				accept: 'application/json',
				Authorization: `Bearer ${PUBLIC_TMDB_API_KEY}`
			}
		};

		const res = await fetch(`https://api.themoviedb.org/3/movie/upcoming?page=1`, options);

		if (!res.ok) {
			throw new Error('Failed to fetch movie changes');
		}

		const data = await res.json();

		return {
			movies: data.results || []
		};
	} catch (error) {
		console.error('Error fetching movie changes:', error);
		return {
			movies: []
		};
	}
};
