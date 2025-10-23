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

	let loading: boolean = $state(false);
	let showResults: boolean = $state(false);
	let movies = $state([]);
	let error = $state('');

	const logout = () => {
		auth.logout();
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
		<AppBar.Lead>
			<button type="button" class="btn-icon btn-icon-lg hover:preset-tonal"><MenuIcon /></button>
		</AppBar.Lead>
		<AppBar.Headline class="flex justify-center gap-2">
			{#if auth.isAuthenticated}
				{#if auth.isAdmin}
					<a href="/admin/dashboard">Dashboard</a>
					<a href="/admin">Home</a>
					<a href="/admin/bucket">Bucket</a>
					<a href="/admin/slot">Slot</a>
				{/if}
			{/if}

			<!-- search bar -->
			<SearchMovies onsearch={handleSearch} />
		</AppBar.Headline>
		<AppBar.Trail class="items-center justify-end">
			<ThemeController />
			<!-- <button type="button" class="btn preset-filled-primary-500 rounded-none uppercase text-white"
				>buy tickets
			</button> -->
			{#if auth.isAuthenticated}
				<Avatar class="size-8 p-3">
					<Avatar.Fallback>{fullname(auth.user?.name)}</Avatar.Fallback>
				</Avatar>
				<button class="btn preset-filled-primary-500 uppercase" onclick={logout}>Login out</button>
			{:else}
				<a class="btn preset-tonal-surface uppercase" href="/login">login</a>
			{/if}
		</AppBar.Trail>
	</AppBar.Toolbar>
</AppBar>

{#if showResults}
	<SearchMovieModal bind:isVisible={showResults} {loading} {movies} />
{/if}
