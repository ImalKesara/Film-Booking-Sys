<script lang="ts">
	import { page } from '$app/state';
	import { toaster } from '$lib/states/toaster.svelte';
	import type { HallDto, Locations, MovieDto } from '$lib/types';
	import { onMount } from 'svelte';

	let data: MovieDto | null = $state(null);
	const paramId = page.params.slug;
	let filmHall: HallDto[] = $state([]);
	let locations: Locations[] = $state([]);
	let loading: boolean = $state(true);
	let time = $state(null);
	let date = $state(null);
	let price = $state(800);
	let location = $state(0);
	let hall: HallDto | null = $state(null);
	const prices = [800, 1200, 1800, 2400, 3500];
	const filteredHalls = $derived(locations.find((loc) => loc.locationId === location)?.halls || []);

	async function submitMovieShow(e) {
		e.preventDefault();
		const token = localStorage.getItem('token');
		const response = await fetch('/api/admin/movie-show', {
			headers: {
				'Content-type': 'application/json',
				Authorization: `Bearer ${token}`
			},
			method: 'POST',
			body: JSON.stringify({
				showDate: date,
				showTime: time,
				price: price,
				availableSeats: hall?.totalSeats,
				movie: data?.movieId,
				hall: hall?.hallId
			})
		});
		if (!response.ok) {
			toaster.error({
				title: 'Try again'
			});
		}

		toaster.success({
			title: 'Added Success',
			description: 'Movie added successfully'
		});
	}

	onMount(async () => {
		const token = localStorage.getItem('token');
		const options = {
			headers: {
				'Content-Type': 'application/json',
				Authorization: `Bearer ${token}`
			}
		};

		try {
			const [movie, location, hall] = await Promise.all([
				fetch(`/api/admin/movie/${paramId}`, options),
				fetch('/api/admin/location', options),
				fetch('/api/admin/hall', options)
			]);

			if (!movie.ok) {
				throw new Error('Result not found');
			}
			data = await movie.json();
			locations = await location.json();
			filmHall = await hall.json();
		} catch (error) {
			console.error(error);
		} finally {
			loading = false;
		}
	});
</script>

<section class="mx-auto my-5 max-w-5xl">
	<div class="card p-4">
		{#if loading}
			Loading...
		{:else if data}
			<div class="card preset-outlined-primary-500 grid grid-cols-2 gap-x-2 p-4">
				<div>
					<img
						class="h-[450px] w-full object-cover"
						src={`https://image.tmdb.org/t/p/original/${data.posterUrl}`}
						alt="Upcoming movies"
					/>
				</div>
				<div class="flex flex-col justify-between p-5 text-center">
					<h1>{data.title}</h1>
					<p>{data.description}</p>

					<!-- Show Time -->
					<div class="grid gap-y-2 p-5">
						<form onsubmit={submitMovieShow} class="space-y-2">
							<input
								class="input"
								type="time"
								id="appointment"
								name="appointment"
								min="09:00"
								max="18:00"
								required
								bind:value={time}
							/>

							<input type="date" class="input" id="start" name="trip-start" bind:value={date} />

							<label class="label">
								<span class="label-text text-left">Select price</span>
								<select class="select" bind:value={price}>
									{#each prices as price}
										<option value={price}>{price}</option>
									{/each}
								</select>
							</label>

							<div class="grid grid-cols-3 items-center gap-x-2">
								<label class="label">
									<span class="label-text text-left">Location</span>
									<select class="select" bind:value={location}>
										{#each locations as location}
											<option value={location.locationId}>{location.name}</option>
										{/each}
									</select>
								</label>

								<label class="label">
									<span class="label-text text-left">Halls</span>
									<select class="select" bind:value={hall}>
										{#each filteredHalls as hall}
											<option value={hall}>{hall.name}</option>
										{/each}
									</select>
								</label>

								<h1 class="flex flex-col text-sm">
									Total Seats <span>{hall?.totalSeats ? hall.totalSeats : 0}</span>
								</h1>
							</div>

							<button type="submit" class="btn preset-filled-tertiary-500">Submit</button>
						</form>
					</div>
				</div>
			</div>
		{/if}
	</div>
</section>
