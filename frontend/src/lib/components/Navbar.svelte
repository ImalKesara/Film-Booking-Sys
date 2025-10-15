<script lang="ts">
	import { MenuIcon } from '@lucide/svelte';
	import { AppBar } from '@skeletonlabs/skeleton-svelte';
</script>

<AppBar>
	<AppBar.Toolbar class="grid-cols-[1fr_2fr_1fr]">
		<AppBar.Lead>
			<button type="button" class="btn-icon btn-icon-lg hover:preset-tonal"><MenuIcon /></button>
		</AppBar.Lead>
		<AppBar.Headline class="flex justify-center gap-2">
			<!-- <p>Home</p>
			<p>Movies</p> -->
		</AppBar.Headline>
		<AppBar.Trail class="justify-end">
			<button type="button" class="btn preset-filled-primary-500 rounded-none uppercase text-white"
				>buy tickets</button
			>
			<button type="button" class="btn preset-tonal-surface uppercase">login</button>
		</AppBar.Trail>
	</AppBar.Toolbar>
</AppBar>
