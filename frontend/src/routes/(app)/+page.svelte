<script lang="ts">
	import MovieShow from '$lib/components/Movieshow.svelte';
	import Carousel from '$lib/components/Carousel.svelte';

	let { data } = $props();
</script>

<svelte:head>
	<title>Welcome to Film Booking System</title>
</svelte:head>

<main class="max-w-full space-y-8">
	<section class="flex flex-col items-center">
		<!-- Coming soon movies -->
		<Carousel movies={data.movies} />

		<!-- Movie show  -->
		<MovieShow movies={data.movies} />
	</section>
</main>
