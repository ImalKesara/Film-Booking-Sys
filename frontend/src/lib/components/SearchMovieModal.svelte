<script lang="ts">
	import { Dialog, Portal } from '@skeletonlabs/skeleton-svelte';
	let { isVisible = $bindable(), loading, movies } = $props();
	import { fade } from 'svelte/transition';
</script>

<Dialog open={isVisible} modal={true} onOpenChange={(d) => (isVisible = d.open)}>
	<Portal>
		<Dialog.Backdrop class="bg-surface-50-950/50 fixed inset-0 z-50" />
		<Dialog.Positioner class="fixed inset-0 z-50 mt-20 flex items-start justify-center">
			<Dialog.Content class="card bg-surface-100-900 w-4xl space-y-2 p-4 shadow-xl">
				{#if loading}
					Loading...
				{:else if movies.length > 0 && movies}
					<div class="grid grid-cols-5 gap-x-2">
						{#each movies as movie}
							<div class="card preset-tonal-primary-500 p-4">
								<header>
									<img
										class="h-[250px] w-full object-cover"
										src={`https://image.tmdb.org/t/p/original/${movie.posterUrl}`}
										alt="Upcoming movies"
									/>
								</header>
								<article class="space-y-4 p-4">
									<div>
										<h2 class="h6 text-sm line-clamp-1">{movie.title}</h2>
									</div>
									<p class="text-xs opacity-60 line-clamp-2">
										{movie.description}
									</p>
								</article>
							</div>
						{/each}
					</div>
				{:else}
					No results found
				{/if}
				<Dialog.CloseTrigger
					class="btn preset-tonal"
					onclick={() => (isVisible = false)}>Close</Dialog.CloseTrigger
				>
			</Dialog.Content>
		</Dialog.Positioner>
	</Portal>
</Dialog>
