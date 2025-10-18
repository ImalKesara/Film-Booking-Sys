import type { PageLoad } from './$types';
import { PUBLIC_TMDB_API_KEY } from '$env/static/public';
import { browser } from '$app/environment';
import { redirect } from '@sveltejs/kit';

export const load: PageLoad = async ({ fetch, url }) => {
	if (browser) {
		const token = localStorage.getItem('token');
		if (!token) {
			throw redirect(302, '/login');
		}
	}

	const token = localStorage.getItem('token');
	const response = await fetch('/api/auth/me', {
		headers: {
			Authorization: `Bearer ${token}`
		}
	});

	if (!response.ok) {
		localStorage.removeItem('token');
		throw redirect(302, '/login');
	}

	const user = await response.json();
	if (user.role !== 'ADMIN') {
		throw redirect(302, '/me');
	}

	try {
		const page = Number(url.searchParams.get('page') || '1');

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

		return {
			user,
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
