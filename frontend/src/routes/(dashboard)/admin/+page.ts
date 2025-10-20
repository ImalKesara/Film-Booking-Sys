import type { PageLoad } from './$types';
import { PUBLIC_TMDB_API_KEY } from '$env/static/public';

export const load: PageLoad = async ({ fetch, url }) => {
	const token = localStorage.getItem('token');
	try {
		const page = Number(url.searchParams.get('page') || '1');

		const options = {
			method: 'GET',
			headers: {
				accept: 'application/json',
				Authorization: `Bearer ${PUBLIC_TMDB_API_KEY}`
			}
		};

		const [moviesTmdb, moviesSpring] = await Promise.all([
			fetch(`https://api.themoviedb.org/3/movie/upcoming?language=en-US&page=${page}`, options),
			fetch('/api/admin/movie', {
				headers: {
					'Content-Type': 'application/json',
					Authorization: `Bearer ${token}`
				}
			})
		]);

		if (!moviesTmdb.ok) {
			throw new Error('Failed to fetch movie changes');
		}

		if (!moviesSpring.ok) {
			throw new Error('Failed to fetch admin movie data');
		}

		const data = await moviesTmdb.json();
		const springMovies = await moviesSpring.json();

		return {
			springMovies,
			movies: data.results || [],
			totalPages: data.total_pages || 1,
			currentPage: page,
			totalResult: data.total_results
		};
	} catch (error) {
		console.error('Error fetching movie changes:', error);
		return {
			movies: []
		};
	}
};
