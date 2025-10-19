import { browser } from '$app/environment';
import { redirect } from '@sveltejs/kit';
import type { PageLoad } from './$types';

export const load: PageLoad = async ({ fetch }) => {
	const token = localStorage.getItem('token');
	if (browser) {
		if (!token) {
			redirect(302, '/login');
		}
	}

	const response = await fetch('/api/auth/me', {
		headers: {
			Authorization: `Bearer ${token}`
		}
	});

	if (!response.ok) {
		redirect(302, '/login');
	}

	const user = await response.json();

	if (user.role !== 'USER') {
		redirect(302, '/admin');
	}

	return {
		user
	};
};
