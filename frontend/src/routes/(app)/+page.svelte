<script lang="ts">
	import Carousel from '$lib/components/Carousel.svelte';

	let { data } = $props();
</script>

<svelte:head>
	<title>Welcome to Film Booking System</title>
</svelte:head>

<main class="max-w-full space-y-8">
	<section class="flex flex-col items-center">
		<Carousel movies={data.movies} />
	</section>
</main>
