<script lang="ts">
	import { XIcon } from '@lucide/svelte';
	import { Dialog, Portal } from '@skeletonlabs/skeleton-svelte';
	let { showDrawer } = $props();
</script>

<Dialog defaultOpen={showDrawer}>
	<Portal>
		<Dialog.Backdrop
			class="bg-surface-50-950/50 transition-discrete starting:data-[state=open]:opacity-0 fixed inset-0 z-50 opacity-0 transition data-[state=open]:opacity-100"
		/>
		<Dialog.Positioner class="fixed inset-0 z-50 flex justify-start">
			<Dialog.Content
				class="card bg-surface-100-900 w-sm transition-discrete starting:data-[state=open]:opacity-0 starting:data-[state=open]:-translate-x-full h-screen -translate-x-full space-y-4 p-4 opacity-0 shadow-xl transition data-[state=open]:translate-x-0 data-[state=open]:opacity-100"
			>
				<header class="flex items-center justify-between">
					<Dialog.Title class="text-2xl font-bold">Drawer Title</Dialog.Title>
					<Dialog.CloseTrigger class="btn-icon preset-tonal" >
						<XIcon />
					</Dialog.CloseTrigger>
				</header>
				<p>This is example content for the slide out drawer panel.</p>
			</Dialog.Content>
		</Dialog.Positioner>
	</Portal>
</Dialog>
