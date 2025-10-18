<script lang="ts">
	import { AppBar } from '@skeletonlabs/skeleton-svelte';
</script>

<AppBar>
	<AppBar.Toolbar class="grid-cols-[1fr_2fr_1fr]">
		<AppBar.Lead></AppBar.Lead>
		<AppBar.Headline class="flex justify-center gap-2">
			<p>© {new Date().getFullYear()} MovieTickets Inc.</p>
		</AppBar.Headline>
		<AppBar.Trail class="justify-end"></AppBar.Trail>
	</AppBar.Toolbar>
</AppBar>
