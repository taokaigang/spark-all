package com.spark.bean;

import java.util.Objects;

public class Score {
    private String uid;
    private String subject_id;
    private String score;

    public Score() {
    }

    public Score(String uid, String subject_id, String score) {
        this.uid = uid;
        this.subject_id = subject_id;
        this.score = score;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(String subject_id) {
        this.subject_id = subject_id;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Score score1 = (Score) o;
        return Objects.equals(uid, score1.uid) && Objects.equals(subject_id, score1.subject_id) && Objects.equals(score, score1.score);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uid, subject_id, score);
    }

    @Override
    public String toString() {
        return "Score{" +
                "uid='" + uid + '\'' +
                ", subject_id='" + subject_id + '\'' +
                ", score='" + score + '\'' +
                '}';
    }


}
