import { redirect } from '@sveltejs/kit';
import type { LayoutLoad } from './$types';
import { browser } from '$app/environment';

export const load: LayoutLoad = async ({fetch}) => {
	const token = localStorage.getItem('token');
	if (browser) {
		if (!token) {
			throw redirect(302, '/login');
		}
	}

	try {
		const response = await fetch('/api/auth/me', {
			headers: {
				Authorization: `Bearer ${token}`
			}
		});

		if (!response.ok) {
			localStorage.removeItem('token');
			redirect(302, '/login');
		}

		const user = await response.json();
		if (user.role !== 'ADMIN') {
			redirect(302, '/me');
		}

		return { user };
	} catch (error) {
		console.error('Auth error:', error);
	}

	return {};
};
