export interface MovieType {
	adult: boolean;
	backdrop_path: string | null;
	genre_ids: number[];
	id: number;
	original_language: string;
	original_title: string;
	overview: string;
	popularity: number;
	poster_path: string | null;
	release_date: string;
	title: string;
	video: boolean;
	vote_average: number;
	vote_count: number;
}

export interface MovieDto {
	movieId: number;
	title: string;
	description: string;
	rating: number;
	posterUrl: string | null;
	backDropPathUrl: string | null;
	tmdbId: string;
	createdAt: string;
}
