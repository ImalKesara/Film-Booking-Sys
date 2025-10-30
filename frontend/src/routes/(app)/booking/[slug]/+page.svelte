<script lang="ts">
	import { goto, invalidateAll } from '$app/navigation';
	import { page } from '$app/state';
	import Payment from '$lib/components/Payment.svelte';
	import { auth } from '$lib/states/auth.svelte';
	import { toaster } from '$lib/states/toaster.svelte';
	import { seatStatus, type CalculateDiscountPrice, type MovieDto, type Seats } from '$lib/types';
	import { onMount } from 'svelte';
	const slug = page.params.slug;
	let movieData: MovieDto | null = $state(null);
	let seatsData: Seats[] = $state([]);
	let selectedSeats: number[] = $state([]);
	let movieShow = $state(null);
	let paymentToggleModal: boolean = $state(false);
	const token = localStorage.getItem('token');
	let loyalPoints: CalculateDiscountPrice | null = $state(null);

	function toggleSeat(seatId: number) {
		if (selectedSeats.includes(seatId)) {
			selectedSeats = selectedSeats.filter((id) => id !== seatId);
		} else {
			selectedSeats = [...selectedSeats, seatId];
		}
	}

	async function handlePayment() {
		const response = await fetch('/api/auth-user/seatBooking/seats-with-payment', {
			headers: {
				'Content-Type': 'application/json',
				Authorization: `Bearer ${token}`
			},
			method: 'POST',
			body: JSON.stringify({
				userId: loyalPoints?.userId,
				showId: slug,
				seatIds: selectedSeats,
				paymentMethod: 'CARD'
			})
		});
		if (response.ok) {
			toaster.success({
				title: 'Success',
				description: 'Book confirmed'
			});

			goto('/');
		} else {
			toaster.error({
				title: 'Something wrong',
				description: 'Try again !!!'
			});
		}
	}

	const getLoyalPoints = async () => {
		const userId = auth.user?.id;
		const response = await fetch(
			`/api/loyalty-point/reward?userId=${Number(userId)}&amount=${20000}`,
			{
				headers: {
					'Content-Type': 'application/json',
					Authorization: `Bearer ${token}`
				}
			}
		);
		const result = await response.json();
		loyalPoints = result;
	};

	onMount(async () => {
		const options = {
			headers: {
				'Content-Type': 'application/json',
				Authorization: `Bearer ${token}`
			}
		};

		const movieId = page.url.searchParams.get('slug');

		const [movie, seats, movieshow] = await Promise.all([
			fetch(`/api/user/movie/${movieId}`),
			fetch(`/api/auth-user/seatBooking/${slug}/seats`, options),
			fetch(`/api/auth-user/seatBooking/${slug}`, options)
		]);
		if (!movie.ok) throw new Error('Movie not found');
		if (!seats.ok) throw new Error('Seats are not found');
		if (!movieshow.ok) throw new Error('Seats are not found');

		// data for movies
		const data = await movie.json();

		// data for total seats
		const seatBooking = await seats.json();

		// data for price
		const dataMovieShow = await movieshow.json();

		movieData = data;
		seatsData = seatBooking;
		movieShow = dataMovieShow;
	});
</script>

<section class="p-10">
	<div class="mx-auto max-w-4xl">
		<div class="grid items-center gap-6 lg:grid-cols-3">
			<div class="flex flex-col items-center lg:col-span-2">
				<div class="mb-6 w-full max-w-2xl">
					<div
						class="h-12 rounded-[55%] border-t-2 border-gray-300/40 bg-gradient-to-b from-gray-300/20 to-transparent"
					></div>
					<p class="mt-2 text-center text-xs uppercase tracking-wider text-gray-500">Screen</p>
				</div>

				<div class="my-8 grid grid-cols-10 gap-2">
					{#each seatsData as seat}
						<button
							type="button"
							disabled={seat.status === 'BOOKED'}
							class="h-8 w-8 rounded text-xs font-semibold transition-all duration-200 hover:scale-110
								{seat.status === 'BOOKED'
								? 'preset-tonal-success cursor-not-allowed text-gray-500 opacity-50'
								: selectedSeats.includes(seat.id)
									? 'btn preset-filled-warning-500'
									: 'btn preset-tonal-secondary'}"
							onclick={() => {
								if (seat.status !== 'BOOKED') {
									toggleSeat(seat.id);
								}
							}}
						>
							{seat.id}
						</button>
					{/each}
				</div>

				<div class="flex justify-center gap-4 text-xs">
					<div class="flex items-center gap-1">
						<div class="preset-tonal-secondary h-4 w-4 rounded"></div>
						<span class="text-gray-400">Available</span>
					</div>
					<div class="flex items-center gap-1">
						<div class="preset-filled-warning-500 h-4 w-4 rounded"></div>
						<span class="text-gray-400">Selected</span>
					</div>
					<div class="flex items-center gap-1">
						<div class="preset-tonal-success h-4 w-4 rounded"></div>
						<span class="text-gray-400">Booked</span>
					</div>
				</div>
			</div>

			<div class="flex w-full flex-col gap-4">
				<h1 class="text-center text-xl font-bold">{movieData?.title || 'Loading...'}</h1>
				<div class="mx-auto w-full max-w-sm overflow-hidden rounded-lg shadow-xl">
					<img
						class="h-full w-full object-cover"
						src={`https://image.tmdb.org/t/p/original/${movieData?.posterUrl}`}
						alt={movieData?.title || 'Movie poster'}
					/>
				</div>
			</div>

			<section class="col-span-3">
				<div class="space-y-2 rounded-lg p-4">
					<div class="flex items-center justify-between">
						<span class="text-gray-400">Total:</span>
						<span>{selectedSeats.length}</span>
					</div>

					<hr class="hr" />

					<div class="flex items-center justify-between">
						<span class="text-gray-400">Amount</span>
						<span class="text-lg font-semibold">
							{selectedSeats.length * movieShow?.price}
						</span>
					</div>
				</div>

				<div class="flex flex-col items-center">
					<button
						class="btn transition-all duration-200
						{selectedSeats.length === 0
							? 'cursor-not-allowed  text-gray-500'
							: ' preset-filled-warning-500 text-white'}"
						onclick={() => {
							paymentToggleModal = true;
							getLoyalPoints();
						}}
						disabled={selectedSeats.length === 0}
					>
						{selectedSeats.length === 0
							? 'Select Seats'
							: `Book ${selectedSeats.length} Seat${selectedSeats.length > 1 ? 's' : ''}`}
					</button>
				</div>
			</section>
		</div>
	</div>
</section>

{#if paymentToggleModal}
	<Payment
		bind:paymentToggle={paymentToggleModal}
		amount={selectedSeats.length * movieShow?.price}
		totalSeats={selectedSeats.length}
		onConfirm={handlePayment}
	/>
{/if}
