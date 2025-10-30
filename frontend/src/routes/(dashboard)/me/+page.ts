import { auth } from '$lib/states/auth.svelte';
import type { PageLoad } from './$types';

export const load: PageLoad = async ({ fetch }) => {
	await auth.fetchUser();
	const userID = auth.user?.id;

	const token = localStorage.getItem('token');
	const response = await fetch(`/api/notification/${userID}`, {
		headers: {
			'Content-type': 'application/json',
			Authorization: `Bearer ${token}`
		}
	});
	const notification = await response.json();
	console.log(notification);
	return { notification };
};
