<script lang="ts">
	import { MenuIcon } from '@lucide/svelte';
	import { AppBar } from '@skeletonlabs/skeleton-svelte';
	import ThemeController from './ThemeController.svelte';
	import { auth } from '$lib/states/auth.svelte';
	import { goto } from '$app/navigation';
	import { Avatar } from '@skeletonlabs/skeleton-svelte';
	import { fullname } from '$lib/utils';
	import SearchMovies from './SearchMovies.svelte';
	import SearchMovieModal from './SearchMovieModal.svelte';
	import Drawer from './Drawer.svelte';

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
	$inspect(drawerCollapse);
</script>

<AppBar>
	<AppBar.Toolbar class="grid-cols-[1fr_2fr_1fr]">
		<AppBar.Lead class="flex gap-x-1">
			{#if auth.isAuthenticated}
				<button
					type="button"
					class="btn-icon btn-icon-lg hover:preset-tonal"
					onclick={() => (drawerCollapse = !drawerCollapse)}><MenuIcon /></button
				>
			{/if}
		</AppBar.Lead>
		<AppBar.Headline class="flex justify-center gap-2">
			{#if auth.isAuthenticated}
				{#if auth.isAdmin}
					<a href="/admin/dashboard">Dashboard</a>
					<a href="/admin">Home</a>
					<a href="/admin/bucket">Bucket</a>
				{/if}
			{:else}
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
					class="btn preset-filled-primary-500 btn-sm rounded-none uppercase font-semibold"
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
