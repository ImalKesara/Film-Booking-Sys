<script lang="ts">
	import { goto } from '$app/navigation';
	import { auth } from '$lib/states/auth.svelte';
	import { TriangleAlertIcon } from '@lucide/svelte';
	import { onMount } from 'svelte';

	let email: string = $state('');
	let password: string = $state('');
	let name: string = $state('');

	let loading: boolean = $state(false);
	let error: string | null = $state(null);
	// const register = async (e: Event) => {
	// 	e.preventDefault();
	// 	try {
	// 		loading = true;
	// 		const response = await fetch('/api/auth/register', {
	// 			method: 'POST',
	// 			body: JSON.stringify({
	// 				dateOfBirth: '1990-01-01',
	// 				email: email,
	// 				name: name,
	// 				password: password,
	// 				phone: '098222999',
	// 				userRole: 'USER'
	// 			})
	// 		});
	// 		if (response.ok) {
	// 			const data = await response.json();
	// 			const token = data.token;

	// 			auth.login(token);
	// 			await auth.fetchUser();

	// 			goto('/login');
	// 		} else {
	// 			console.error('register failed');
	// 		}
	// 	} catch (err) {
	// 		console.error('Login error:', err);
	// 	} finally {
	// 		loading = false;
	// 	}
	// };

	const register = async (e: Event) => {
		e.preventDefault();
		try {
			loading = true;
			const response = await fetch('/api/auth/register', {
				method: 'POST',
				headers: {
					'Content-Type': 'application/json'
				},
				body: JSON.stringify({
					dateOfBirth: '1990-01-01',
					email: email,
					name: name,
					password: password,
					phone: '098222999',
					userRole: 'USER'
				})
			});

			if (response.ok) {
				const message = await response.text();
				console.log('Server response:', message);

				goto('/login');
			} else {
				const errorText = await response.text();
				console.error('Register failed:', errorText);
			}
		} catch (err) {
			console.error('Register error:', err);
		} finally {
			loading = false;
		}
	};

	onMount(() => {
		if (auth.isAuthenticated) {
			if (auth.isAdmin) {
				goto('/admin');
			} else {
				goto('/me');
			}
		}
	});
</script>

<form
	onsubmit={register}
	class="card preset-filled-surface-100-900 flex w-full max-w-sm flex-col gap-y-3 p-4"
>
	{#if error}
		<div
			class="card preset-outlined-error-500 grid grid-cols-1 items-center gap-4 p-4 lg:grid-cols-[auto_1fr_auto]"
		>
			<TriangleAlertIcon />
			<div>
				<p class="font-bold">Error</p>
				<p class="text-xs opacity-60">{error}</p>
			</div>
			<div class="flex gap-1">
				<button class="btn preset-tonal hover:preset-filled">Dismiss</button>
			</div>
		</div>
	{/if}

	<label class="space-y-2">
		<span class="label-text">Username</span>
		<input class="input" type="text" bind:value={name} />
	</label>

	<label class="email space-y-2">
		<span class="label-text">Email</span>
		<input class="input" type="email" bind:value={email} />
	</label>

	<label class="password space-y-2">
		<span class="label-text">Password</span>
		<input class="input" type="password" bind:value={password} />
	</label>

	<button
		type="submit"
		class="btn preset-filled-primary-500 rounded-none font-semibold text-white"
		disabled={loading}>{loading ? 'Register' : 'Register'}</button
	>
	<p class="text-center text-sm">
		Do you have an account? <a href="/login" class="text-secondary-500">Login</a>
	</p>
</form>
