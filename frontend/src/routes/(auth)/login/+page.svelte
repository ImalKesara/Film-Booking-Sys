<script lang="ts">
	let email: string = $state('');
	let password: string = $state('');
	const login = async () => {};
</script>

<form
	onsubmit={login}
	class="card preset-filled-surface-100-900 flex w-full max-w-md flex-col gap-y-3 p-4"
>
	<label class="email space-y-2">
		<span class="label-text">Email</span>
		<input class="input" type="email" bind:value={email} />
	</label>

	<label class="password space-y-2">
		<span class="label-text">Password</span>
		<input class="input" type="password" bind:value={password} />
	</label>

	<button type="submit" class="btn preset-outlined-surface-500">Login</button>
</form>
