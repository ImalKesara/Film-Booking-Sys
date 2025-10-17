<script lang="ts">
	import { Switch } from '@skeletonlabs/skeleton-svelte';

   let checked : boolean = $state(false)

   $effect(()=>{
      const mode = localStorage.getItem('mode') || 'light';
      checked = mode === 'dark' ? true : false;
   })

   

</script>

