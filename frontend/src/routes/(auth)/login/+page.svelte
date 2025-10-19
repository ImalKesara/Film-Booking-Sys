<script lang="ts">
	import { goto } from '$app/navigation';
	import { auth } from '$lib/states/auth.svelte';
	import { TriangleAlertIcon } from '@lucide/svelte';
	import { onMount } from 'svelte';

	let email: string = $state('');
	let password: string = $state('');
	let loading: boolean = $state(false);
	let error: string | null = $state(null);
	const login = async (e: Event) => {
		e.preventDefault();
		try {
			loading = true;
			const response = await fetch('/api/auth/login', {
				method: 'POST',
				headers: {
					'Content-Type': 'application/json'
				},
				body: JSON.stringify({ email, password })
			});
			if (response.ok) {
				const data = await response.json();
				const token = data.token;

				auth.login(token);

				if (auth.user?.role === 'ADMIN') {
					goto('/admin');
				} else {
					goto('/me');
				}
			} else {
				// Handle login error
				error = 'Invalid email or password';
				console.error('login failed');
			}
		} catch (err) {
			console.error('Login error:', err);
		} finally {
			loading = false;
		}
	};

	onMount(() => {
		if (auth.isAuthenticated) {
			if (auth.isAdmin) {
				goto('/admin');
			} else {
				goto('/');
			}
		}
	});
</script>

<form
	onsubmit={login}
	class="card preset-filled-surface-100-900 flex w-full max-w-md flex-col gap-y-3 p-4"
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

	<label class="email space-y-2">
		<span class="label-text">Email</span>
		<input class="input" type="email" bind:value={email} />
	</label>

	<label class="password space-y-2">
		<span class="label-text">Password</span>
		<input class="input" type="password" bind:value={password} />
	</label>

	<button type="submit" class="btn preset-outlined-surface-500" disabled={loading}
		>{loading ? 'Logging in...' : 'Login'}</button
	>
	<p class="text-center text-sm">
		Don't have an account? <a href="/register" class="text-primary-500">Register</a>
	</p>
</form>
