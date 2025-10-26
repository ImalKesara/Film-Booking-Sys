<script lang="ts">
	import { goto } from '$app/navigation';
	import { page } from '$app/state';
	import { auth } from '$lib/states/auth.svelte';
	import type { MovieDto } from '$lib/types';
	import { onMount } from 'svelte';
	const slug = page.params.slug;
	let data: MovieDto | null = $state(null);

	const handleUserAuth = () => {
		if (auth.isAuthenticated && auth.isAdmin) {
			goto('/admin');
			return;
		}

		if (!auth.isAuthenticated) {
			const intendedUrl = `/booking/${slug}`;
			sessionStorage.setItem('redirectAfterLogin', intendedUrl);
			goto('/login');
		} else {
			goto(`/booking/${slug}`);
		}
	};

	onMount(async () => {
		const response = await fetch(`/api/user/movie/${slug}`);
		if (!response.ok) {
			throw new Error('Something wrong !!!!');
		}
		data = await response.json();
	});
</script>

<section class="relative">
	<img
		class="h-[600px] w-full object-cover opacity-30"
		src={`https://image.tmdb.org/t/p/original/${data?.backDropPathUrl}`}
		alt={data?.title || 'Featured movie'}
	/>
	<div class="absolute inset-0 flex flex-col items-center justify-center px-4 text-center">
		<h1 class="mb-4 text-5xl font-bold text-white opacity-100">{data?.title || 'Loading...'}</h1>
		<p class="mb-8 max-w-2xl text-lg text-white/90">{data?.description || ''}</p>

		<button class="btn preset-filled-tertiary-500 rounded-none uppercase" onclick={handleUserAuth}
			>buy tickets online</button
		>
	</div>
</section>
