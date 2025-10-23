<script lang="ts">
	import { Dialog, Portal } from '@skeletonlabs/skeleton-svelte';
	let { isVisible = $bindable(), loading, movies } = $props();
</script>

<Dialog defaultOpen={isVisible} modal={true} >
	<Portal>
		<Dialog.Backdrop class="bg-surface-50-950/50 fixed inset-0 z-50" />
		<Dialog.Positioner class="fixed inset-0 z-50 flex items-start mt-20 justify-center">
			<Dialog.Content class="card bg-surface-100-900 w-md space-y-2 p-4 shadow-xl">
				{#if loading}
					Loading...
				{:else if movies.length > 0 && movies}
					{#each movies as movie}
						<main
							class="card preset-filled-surface-100-900 border-surface-200-800 card-hover divide-surface-200-800 block max-w-lg divide-y overflow-hidden border-[1px]"
						>
							<header>
								<img
									class="h-[250px] w-full object-cover"
									src={`https://image.tmdb.org/t/p/original/${movie.posterUrl}`}
									alt="Upcoming movies"
								/>
							</header>

							<article class="space-y-4 p-4">
								<div>
									<h2 class="line-clamp-1 text-sm text-gray-500">{movie.title}</h2>
								</div>
							</article>
						</main>
					{/each}
				{:else}
					No results found
				{/if}
				<Dialog.CloseTrigger class="btn preset-tonal" onclick={() => (isVisible = false)}
					>Close</Dialog.CloseTrigger
				>
			</Dialog.Content>
		</Dialog.Positioner>
	</Portal>
</Dialog>
