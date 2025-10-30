<script lang="ts">
	import { AppBar } from '@skeletonlabs/skeleton-svelte';
	import ThemeController from './ThemeController.svelte';
	import { auth } from '$lib/states/auth.svelte';
	import { goto } from '$app/navigation';
	import SearchMovies from './SearchMovies.svelte';
	import SearchMovieModal from './SearchMovieModal.svelte';
	import Drawer from './Drawer.svelte';
	import { page } from '$app/state';

	let loading: boolean = $state(false);
	let showResults: boolean = $state(false);
	let movies = $state([]);
	let error = $state('');
	let drawerCollapse = $state(false);

	const logout = () => {
		auth.logout();
		sessionStorage.removeItem('redirectAfterLogin');
		goto('/');
	};

	async function handleSearch(params: string) {
		loading = true;
		showResults = true;
		try {
			const response = await fetch(`/api/user/movie/search?query=${params}`);
			if (!response.ok) {
				console.error('Check ->');
			}
			const data = await response.json();
			if (Array.isArray(data)) {
				movies = data;
			} else if (data.message) {
				error = data.message;
				console.log(error);
			} else {
				movies = [];
			}
		} catch (error) {
			console.error(error);
		} finally {
			loading = false;
		}
	}
</script>

<AppBar>
	<AppBar.Toolbar class="grid-cols-[1fr_2fr_1fr]">
		<AppBar.Lead class="flex gap-x-1  capitalize">
			{#if auth.isAuthenticated}
				{#if auth.isAdmin}
					<a
						href="/admin/dashboard"
						class="btn btn-sm uppercase"
						class:preset-filled-secondary-500={page.url.pathname === '/admin/dashboard'}
						>Dashboard</a
					>
					<a
						href="/admin"
						class="btn btn-sm uppercase"
						class:preset-filled-secondary-500={page.url.pathname === '/admin'}>Home</a
					>
					<a
						href="/admin/bucket"
						class="btn btn-sm uppercase"
						class:preset-filled-secondary-500={page.url.pathname === '/admin/bucket'}>Bucket</a
					>
				{/if}
			{/if}
		</AppBar.Lead>
		<AppBar.Headline class="flex justify-center gap-2">
			{#if !auth.isAuthenticated}
				<!-- search bar -->
				<SearchMovies onsearch={handleSearch} />
			{/if}
		</AppBar.Headline>
		<AppBar.Trail class="items-center justify-end">
			<ThemeController />
			{#if !auth.isAuthenticated}
				<a class="btn preset-tonal-surface uppercase" href="/login">login</a>
			{:else}
				<button
					class="btn preset-filled-primary-500 btn-sm rounded-none font-semibold uppercase"
					onclick={logout}>Login out</button
				>
			{/if}
		</AppBar.Trail>
	</AppBar.Toolbar>
</AppBar>

{#if showResults}
	<SearchMovieModal bind:isVisible={showResults} {loading} {movies} />
{/if}

{#if drawerCollapse}
	<Drawer showDrawer={drawerCollapse} />
{/if}
