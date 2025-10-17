<script lang="ts">
	let email: string = $state('');
	let password: string = $state('');
	const login = async () => {
		
	};
</script>

<form onsubmit={login} class="flex flex-col gap-y-3">
	<label class="email">
		<span class="label-text">Email Address</span>
		<input class="input w-xl" type="email" placeholder="Email Address" bind:value={email} />
	</label>

	<label class="password">
		<span class="label-text">Password</span>
		<input class="input w-xl" type="password" placeholder="Password" bind:value={password} />
	</label>

	<button type="submit" class="btn preset-filled-surface-500">Login</button>
</form>
