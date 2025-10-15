import type { PageLoad } from './$types';
import { PUBLIC_TMDB_API_KEY } from '$env/static/public';

export const load: PageLoad = async ({ fetch, url }) => {
	try {
		const page = Number(url.searchParams.get('page') || '1');
		console.log('Fetching upcoming movies for page:', page);

		const options = {
			method: 'GET',
			headers: {
				accept: 'application/json',
				Authorization: `Bearer ${PUBLIC_TMDB_API_KEY}`
			}
		};

		const res = await fetch(
			`https://api.themoviedb.org/3/movie/upcoming?language=en-US&page=${page}`,
			options
		);

		if (!res.ok) {
			throw new Error('Failed to fetch movie changes');
		}

		const data = await res.json();

		console.log(data);

		return {
			movies: data.results || [],
			totalPages: data.total_pages || 1,
			currentPage: page
		};
	} catch (error) {
		console.error('Error fetching movie changes:', error);
		return {
			movies: []
		};
	}
};
